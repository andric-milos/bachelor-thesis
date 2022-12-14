package rs.ac.uns.ftn.bachelor_thesis.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.bachelor_thesis.dto.CreateGameDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.NewAppointmentDTO;
import rs.ac.uns.ftn.bachelor_thesis.enumeration.AppointmentPrivacy;
import rs.ac.uns.ftn.bachelor_thesis.enumeration.TeamColor;
import rs.ac.uns.ftn.bachelor_thesis.model.*;
import rs.ac.uns.ftn.bachelor_thesis.repository.AppointmentRepository;
import rs.ac.uns.ftn.bachelor_thesis.repository.GameRepository;
import rs.ac.uns.ftn.bachelor_thesis.repository.PlayerRepository;

import java.util.*;

@Service
public class AppointmentService {
    private AppointmentRepository appointmentRepository;
    private GroupService groupService;
    private PlayerService playerService;
    private GameRepository gameRepository;
    private PlayerRepository playerRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              GroupService groupService,
                              PlayerService playerService,
                              GameRepository gameRepository,
                              PlayerRepository playerRepository) {
        this.appointmentRepository = appointmentRepository;
        this.groupService = groupService;
        this.playerService = playerService;
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
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

    @Transactional
    public Game addGame(CreateGameDTO dto) {
        Optional<Appointment> appointmentOptional = getAppointmentById(dto.getAppointmentId());

        if (appointmentOptional.isEmpty())
            return null;

        Set<Player> teamRedPlayers = new HashSet<>();
        dto.getTeamRed().forEach(email -> {
            /* Getting each player by his email and adding him to the Set of players,
             * which will later be used to form a Team (teamRed) and that Team
             * will then be added to the Game which will be saved in the database. */
            Player player = playerService.getPlayerByEmail(email).get();
            teamRedPlayers.add(player);

            // Updating each player's number of games and saving it to the database.
            player.setNumberOfGames(player.getNumberOfGames() + 1);
            playerRepository.save(player);
        });

        Set<Player> teamBluePlayers = new HashSet<>();
        dto.getTeamBlue().forEach(email -> {
            /* Getting each player by his email and adding him to the Set of players,
             * which will later be used to form a Team (teamBlue) and that Team
             * will then be added to the Game which will be saved in the database. */
            Player player = playerService.getPlayerByEmail(email).get();
            teamBluePlayers.add(player);

            // Updating each player's number of games and saving it to the database.
            player.setNumberOfGames(player.getNumberOfGames() + 1);
            playerRepository.save(player);
        });

        // Initializing a new game - adding teams to it and associating it with the appointment.
        Appointment appointment = appointmentOptional.get();
        Team teamRed = Team.builder().players(teamRedPlayers).build();
        Team teamBlue = Team.builder().players(teamBluePlayers).build();
        Game game = Game.builder()
                .teamRed(teamRed).teamBlue(teamBlue)
                .appointment(appointment).build();

        List<Goal> goals = new ArrayList<>();
        dto.getGoals().forEach(goalDTO -> {
            /* After getting a player by his email, we have all we need to create
             * a new Goal: player, teamColor, game. Then we add that Goal to a List
             * of goals which will later be used to associate them with the game
             * and save all of that in the database. */
            Player player = playerService.getPlayerByEmail(goalDTO.getPlayer()).get();
            Goal goal = Goal.builder()
                    .player(player)
                    .teamColor(TeamColor.valueOf(goalDTO.getTeamColor().toUpperCase()))
                    .game(game)
                    .build();
            goals.add(goal);

            // Updating each player who scored his number of goals and saving it to the database.
            player.setNumberOfGoals(player.getNumberOfGoals() + 1);
            playerRepository.save(player);
        });

        // Updating each player's number of goals per appointment statistic and saving it to the database.
        for (Player player : appointment.getPlayers()) {
            player.setGoalsPerAppointment((double) player.getNumberOfGoals() / player.getAppointments().size());
            playerRepository.save(player);
        }

        // Determining the result of the game.
        int goalsByTeamRed = 0, goalsByTeamBlue = 0;
        for (Goal goal : goals) {
            if (goal.getTeamColor().equals(TeamColor.RED))
                ++goalsByTeamRed;
            else
                ++goalsByTeamBlue;
        }

        // Updating players' number of games won and winning percentages.
        if (goalsByTeamRed > goalsByTeamBlue) {
            /* Players from team Red won the game. -> Incrementing their number of games won
             * and calculating their winning percentages. */
            for (Player player : teamRed.getPlayers()) {
                player.setNumberOfGamesWon(player.getNumberOfGamesWon() + 1);
                player.setWinningPercentage((double) player.getNumberOfGamesWon() / player.getNumberOfGames() * 100);
                playerRepository.save(player);
            }

            // Players from team Blue lost the game. -> Recalculating their winning percentages.
            for (Player player : teamBlue.getPlayers()) {
                player.setWinningPercentage((double) player.getNumberOfGamesWon() / player.getNumberOfGames() * 100);
                playerRepository.save(player);
            }
        } else if (goalsByTeamBlue > goalsByTeamRed) {
            /* Players from team Blue won the game. -> Incrementing their number of games won
             * and calculating their winning percentages. */
            for (Player player : teamBlue.getPlayers()) {
                player.setNumberOfGamesWon(player.getNumberOfGamesWon() + 1);
                player.setWinningPercentage((double) player.getNumberOfGamesWon() / player.getNumberOfGames() * 100);
                playerRepository.save(player);
            }

            // Players from team Red lost the game. -> Recalculating their winning percentages.
            for (Player player : teamRed.getPlayers()) {
                player.setWinningPercentage((double) player.getNumberOfGamesWon() / player.getNumberOfGames() * 100);
                playerRepository.save(player);
            }
        } else {
            // Nobody won the game, it was a draw. -> Recalculating everybody's winning percentages.
            for (Player player : teamBlue.getPlayers()) {
                player.setWinningPercentage((double) player.getNumberOfGamesWon() / player.getNumberOfGames() * 100);
                playerRepository.save(player);
            }
            for (Player player : teamRed.getPlayers()) {
                player.setWinningPercentage((double) player.getNumberOfGamesWon() / player.getNumberOfGames() * 100);
                playerRepository.save(player);
            }
        }

        game.setGoals(goals);
        return gameRepository.save(game);
    }

    public List<Appointment> getAllPublicAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        List<Appointment> publicAppointments = new ArrayList<>();

        appointments.forEach(appointment -> {
            if (appointment.getPrivacy().equals(AppointmentPrivacy.PUBLIC))
                publicAppointments.add(appointment);
        });

        return publicAppointments;
    }
}
