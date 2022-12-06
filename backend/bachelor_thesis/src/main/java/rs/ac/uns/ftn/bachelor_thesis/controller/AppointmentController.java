package rs.ac.uns.ftn.bachelor_thesis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.bachelor_thesis.dto.AppointmentDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.NewAppointmentDTO;
import rs.ac.uns.ftn.bachelor_thesis.model.Appointment;
import rs.ac.uns.ftn.bachelor_thesis.model.Group;
import rs.ac.uns.ftn.bachelor_thesis.model.Player;
import rs.ac.uns.ftn.bachelor_thesis.service.AppointmentService;
import rs.ac.uns.ftn.bachelor_thesis.service.GroupService;
import rs.ac.uns.ftn.bachelor_thesis.service.PlayerService;
import rs.ac.uns.ftn.bachelor_thesis.validation.ValidationUtil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
}
