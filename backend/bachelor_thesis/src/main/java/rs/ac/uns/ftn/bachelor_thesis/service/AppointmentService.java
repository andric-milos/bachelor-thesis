package rs.ac.uns.ftn.bachelor_thesis.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.bachelor_thesis.dto.CreateGameDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.NewAppointmentDTO;
import rs.ac.uns.ftn.bachelor_thesis.enumeration.AppointmentPrivacy;
import rs.ac.uns.ftn.bachelor_thesis.enumeration.TeamColor;
import rs.ac.uns.ftn.bachelor_thesis.model.*;
import rs.ac.uns.ftn.bachelor_thesis.repository.AppointmentRepository;
import rs.ac.uns.ftn.bachelor_thesis.repository.GameRepository;

import java.util.*;

@Service
public class AppointmentService {
    private AppointmentRepository appointmentRepository;
    private GroupService groupService;
    private PlayerService playerService;
    private GameRepository gameRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              GroupService groupService,
                              PlayerService playerService,
                              GameRepository gameRepository) {
        this.appointmentRepository = appointmentRepository;
        this.groupService = groupService;
        this.playerService = playerService;
        this.gameRepository = gameRepository;
    }

    public Appointment createAppointment(NewAppointmentDTO dto) {
        Optional<Group> group = groupService.getGroupById(dto.getGroupId());

        if (group.isPresent()) {
            Appointment newAppointment = new Appointment(
                    group.get(),
                    new Date(dto.getDate()),
                    AppointmentPrivacy.valueOf(dto.getPrivacy().toUpperCase()),
                    new Location(dto.getAddress(), 0.0, 0.0),
                    dto.getCapacity(),
                    dto.getPrice()
            );

            return appointmentRepository.save(newAppointment);
        }

        return null;
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public Appointment addPlayerToAppointment(Appointment appointment, Player player) {
        try {
            if (appointment.getPlayers().add(player)) {
                appointment.setOccupancy(appointment.getOccupancy() + 1);
                return appointmentRepository.save(appointment);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return appointment;
    }
    
    public boolean removePlayerFromAppointment(Appointment appointment, Player player) {
        if (appointment.getPlayers().remove(player)) {
            appointment.setOccupancy(appointment.getOccupancy() - 1);
            appointmentRepository.save(appointment);
            return true;
        }

        return false;
    }

    public Game addGame(CreateGameDTO dto) {
        Optional<Appointment> appointmentOptional = getAppointmentById(dto.getAppointmentId());

        if (appointmentOptional.isEmpty())
            return null;

        Appointment appointment = appointmentOptional.get();

        Set<Player> teamRedPlayers = new HashSet<>();
        dto.getTeamRed().forEach(email -> {
            Optional<Player> player = playerService.getPlayerByEmail(email);
            teamRedPlayers.add(player.get());
        });

        Set<Player> teamBluePlayers = new HashSet<>();
        dto.getTeamBlue().forEach(email -> {
            Optional<Player> player = playerService.getPlayerByEmail(email);
            teamBluePlayers.add(player.get());
        });

        Team teamRed = Team.builder().players(teamRedPlayers).build();
        Team teamBlue = Team.builder().players(teamBluePlayers).build();

        Game game = Game.builder()
                .teamRed(teamRed).teamBlue(teamBlue)
                .appointment(appointment).build();

        List<Goal> goals = new ArrayList<>();
        dto.getGoals().forEach(goalDTO -> {
            Optional<Player> player = playerService.getPlayerByEmail(goalDTO.getPlayer());
            Goal goal = Goal.builder()
                    .player(player.get())
                    .teamColor(TeamColor.valueOf(goalDTO.getTeamColor().toUpperCase()))
                    .game(game)
                    .build();
            goals.add(goal);
        });

        game.setGoals(goals);

        return gameRepository.save(game);
    }
}
