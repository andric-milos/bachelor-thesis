package rs.ac.uns.ftn.bachelor_thesis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.bachelor_thesis.dto.AppointmentDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.CreateGameDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.GameBasicInfoDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.NewAppointmentDTO;
import rs.ac.uns.ftn.bachelor_thesis.enumeration.AppointmentPrivacy;
import rs.ac.uns.ftn.bachelor_thesis.model.Appointment;
import rs.ac.uns.ftn.bachelor_thesis.model.Game;
import rs.ac.uns.ftn.bachelor_thesis.model.Group;
import rs.ac.uns.ftn.bachelor_thesis.model.Player;
import rs.ac.uns.ftn.bachelor_thesis.service.AppointmentService;
import rs.ac.uns.ftn.bachelor_thesis.service.GroupService;
import rs.ac.uns.ftn.bachelor_thesis.service.PlayerService;
import rs.ac.uns.ftn.bachelor_thesis.validation.ValidationUtil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    private AppointmentService appointmentService;
    private ValidationUtil validationUtil;
    private GroupService groupService;
    private PlayerService playerService;

    public AppointmentController(AppointmentService appointmentService,
                                 ValidationUtil validationUtil,
                                 GroupService groupService,
                                 PlayerService playerService) {
        this.appointmentService = appointmentService;
        this.validationUtil = validationUtil;
        this.groupService = groupService;
        this.playerService = playerService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_PLAYER')")
    public ResponseEntity<?> createAppointment(@RequestBody NewAppointmentDTO dto) {
        if (!validationUtil.validateNewAppointmentDTO(dto))
            return new ResponseEntity<>("Invalid input of data!", HttpStatus.BAD_REQUEST);

        Optional<Group> group = groupService.getGroupById(dto.getGroupId());
        if (group.isEmpty())
            return new ResponseEntity<>("Non-existing group!", HttpStatus.BAD_REQUEST);

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Player> player = playerService.getPlayerByEmail(email);

        if (player.isEmpty())
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        if (!group.get().getPlayers().contains(player.get()))
            return new ResponseEntity<>("You're not in the group!", HttpStatus.FORBIDDEN);

        Appointment appointment = appointmentService.createAppointment(dto);

        if (appointment == null)
            return new ResponseEntity<>("Appointment not created!", HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(new AppointmentDTO(appointment), HttpStatus.OK);
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<?> getGroupsAppointments(@PathVariable("id") Long groupId) {
        Optional<Group> group = groupService.getGroupById(groupId);

        if (group.isEmpty())
            return new ResponseEntity<>("Group with id " + groupId + " doesn't exist!", HttpStatus.BAD_REQUEST);

        Set<AppointmentDTO> appointmentSet = new HashSet<>();
        group.get().getAppointments().forEach(appointment -> {
            appointmentSet.add(new AppointmentDTO(appointment));
        });

        return new ResponseEntity<>(appointmentSet, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PLAYER')")
    public ResponseEntity<?> getAppointmentById(@PathVariable("id") Long appointmentId) {
        Optional<Appointment> appointment = appointmentService.getAppointmentById(appointmentId);

        if (appointment.isEmpty())
            return new ResponseEntity<>("Appointment with id " + appointmentId + " not found!", HttpStatus.NOT_FOUND);

        /* Checking if the player has access to this data. */
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (appointment.get().getPrivacy().equals(AppointmentPrivacy.PRIVATE)
                && !groupService.getPlayersEmails(appointment.get().getGroup()).contains(email))
            return new ResponseEntity<>("You're not a member of the group!", HttpStatus.FORBIDDEN);

        AppointmentDTO dto = new AppointmentDTO(appointment.get());

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/attend/{id}")
    @PreAuthorize("hasRole('ROLE_PLAYER')")
    public ResponseEntity<?> attendAppointment(@PathVariable("id") Long appointmentId) {
        Optional<Appointment> appointment = appointmentService.getAppointmentById(appointmentId);

        if (appointment.isEmpty())
            return new ResponseEntity<>("Appointment with id " + appointmentId + " not found!", HttpStatus.NOT_FOUND);

        /* Checking if the player can attend this appointment. */
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (appointment.get().getPrivacy().equals(AppointmentPrivacy.PRIVATE)
                && !groupService.getPlayersEmails(appointment.get().getGroup()).contains(email))
            return new ResponseEntity<>("You're not a member of the group!", HttpStatus.FORBIDDEN);

        if (appointment.get().getOccupancy() >= appointment.get().getCapacity())
            return new ResponseEntity<>("Full capacity!", HttpStatus.BAD_REQUEST);

        Optional<Player> player = playerService.getPlayerByEmail(email);

        Appointment updatedAppointment = appointmentService.addPlayerToAppointment(appointment.get(), player.get());
        if (updatedAppointment == null)
            return new ResponseEntity<>("Something went wrong on the server!", HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(new AppointmentDTO(updatedAppointment), HttpStatus.OK);
    }

    @PutMapping("/cancel/{id}")
    @PreAuthorize("hasRole('ROLE_PLAYER')")
    public ResponseEntity<?> cancelAppointment(@PathVariable("id") Long appointmentId) {
        Optional<Appointment> appointment = appointmentService.getAppointmentById(appointmentId);

        if (appointment.isEmpty())
            return new ResponseEntity<>("Appointment with id " + appointmentId + " not found!", HttpStatus.NOT_FOUND);

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Player> player = playerService.getPlayerByEmail(email);
        if (appointmentService.removePlayerFromAppointment(appointment.get(), player.get()))
            return new ResponseEntity<>("You successfully canceled the appointment!", HttpStatus.OK);

        return new ResponseEntity<>("You were not attending this appointment!", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/game")
    @PreAuthorize("hasRole('ROLE_PLAYER')")
    public ResponseEntity<?> addGame(@RequestBody CreateGameDTO dto) {
        if (!validationUtil.validateCreateGameDTO(dto))
            return new ResponseEntity<>("Invalid input of data!", HttpStatus.BAD_REQUEST);

        Optional<Appointment> appointment = appointmentService.getAppointmentById(dto.getAppointmentId());
        if (appointment.isEmpty())
            return new ResponseEntity<>("Appointment with id " + dto.getAppointmentId() + " not found!", HttpStatus.NOT_FOUND);

        if (!playerService.doPlayersExist(Stream.concat(dto.getTeamRed().stream(), dto.getTeamBlue().stream()).toList()))
            return new ResponseEntity<>("You entered one or more non-existing players!", HttpStatus.BAD_REQUEST);

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Player> player = playerService.getPlayerByEmail(email);

        if (!appointment.get().getPlayers().contains(player.get()))
            return new ResponseEntity<>("You are not an attendee of this appointment!", HttpStatus.FORBIDDEN);

        Game game = appointmentService.addGame(dto);

        return new ResponseEntity<>(new GameBasicInfoDTO(game), HttpStatus.OK);
    }

    @GetMapping("/amIattending/{id}")
    @PreAuthorize("hasRole('ROLE_PLAYER')")
    public ResponseEntity<?> amIattending(@PathVariable("id") Long appointmentId) {
        Optional<Appointment> appointment = appointmentService.getAppointmentById(appointmentId);

        if (appointment.isEmpty())
            return new ResponseEntity<>("Appointment with id " + appointmentId + " not found!", HttpStatus.NOT_FOUND);

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (Player player : appointment.get().getPlayers()) {
            if (player.getEmail().equals(email))
                return new ResponseEntity<>(true, HttpStatus.OK);
        }

        return new ResponseEntity<>(false, HttpStatus.OK);
    }
}
