package rs.ac.uns.ftn.bachelor_thesis.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.bachelor_thesis.dto.AppointmentDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.CreateGameDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.GameBasicInfoDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.NewAppointmentDTO;
import rs.ac.uns.ftn.bachelor_thesis.enumeration.AppointmentPrivacy;
import rs.ac.uns.ftn.bachelor_thesis.enumeration.TeamColor;
import rs.ac.uns.ftn.bachelor_thesis.exception.CustomizableBadRequestException;
import rs.ac.uns.ftn.bachelor_thesis.exception.InternalServerErrorException;
import rs.ac.uns.ftn.bachelor_thesis.exception.InvalidInputDataException;
import rs.ac.uns.ftn.bachelor_thesis.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.bachelor_thesis.exception.UnauthorizedException;
import rs.ac.uns.ftn.bachelor_thesis.mapper.AppointmentMapper;
import rs.ac.uns.ftn.bachelor_thesis.model.Appointment;
import rs.ac.uns.ftn.bachelor_thesis.model.Game;
import rs.ac.uns.ftn.bachelor_thesis.model.Goal;
import rs.ac.uns.ftn.bachelor_thesis.model.Group;
import rs.ac.uns.ftn.bachelor_thesis.model.Location;
import rs.ac.uns.ftn.bachelor_thesis.model.Player;
import rs.ac.uns.ftn.bachelor_thesis.model.Team;
import rs.ac.uns.ftn.bachelor_thesis.repository.AppointmentRepository;
import rs.ac.uns.ftn.bachelor_thesis.repository.GameRepository;
import rs.ac.uns.ftn.bachelor_thesis.repository.PlayerRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.lang.String.format;
import static rs.ac.uns.ftn.bachelor_thesis.exception.ResourceNotFoundException.ResourceType.APPOINTMENT_ID;
import static rs.ac.uns.ftn.bachelor_thesis.util.Constants.MAXIMUM_APPOINTMENT_CAPACITY;
import static rs.ac.uns.ftn.bachelor_thesis.util.Constants.MINIMUM_APPOINTMENT_CAPACITY;
import static rs.ac.uns.ftn.bachelor_thesis.util.ValidationUtil.validateCreateGameDTO;
import static rs.ac.uns.ftn.bachelor_thesis.util.ValidationUtil.validateNewAppointmentDTO;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final GroupService groupService;
    private final PlayerService playerService;
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final AppointmentMapper appointmentMapper;
    private final UserService userService;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              GroupService groupService,
                              PlayerService playerService,
                              GameRepository gameRepository,
                              PlayerRepository playerRepository,
                              AppointmentMapper appointmentMapper,
                              UserService userService) {
        this.appointmentRepository = appointmentRepository;
        this.groupService = groupService;
        this.playerService = playerService;
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.appointmentMapper = appointmentMapper;
        this.userService = userService;
    }

    public AppointmentDTO createAppointment(NewAppointmentDTO dto) {
        if (!validateNewAppointmentDTO(dto)) {
            throw new InvalidInputDataException("Invalid input of data!");
        }

        if (dto.getCapacity() < MINIMUM_APPOINTMENT_CAPACITY || dto.getCapacity() > MAXIMUM_APPOINTMENT_CAPACITY) {
            throw new CustomizableBadRequestException(format("%d is not a valid appointment capacity!", dto.getCapacity()));
        }

        Group group = groupService.getGroupById(dto.getGroupId());

        String email = userService.getEmailOfLoggedInUser();
        Player player;
        try {
            player = playerService.getPlayerByEmail(email);
        } catch (Exception e) {
            throw new UnauthorizedException("Unauthorized!");
        }

        if (!group.getPlayers().contains(player)) {
            throw new UnauthorizedException("You're not in the group!");
        }


        Appointment newAppointment = new Appointment(
                group,
                new Date(dto.getDate()),
                AppointmentPrivacy.valueOf(dto.getPrivacy().toUpperCase()),
                new Location(dto.getAddress(), null, null), // Because locations (longitude & latitude) selected from maps are not implemented yet
                dto.getCapacity(),
                dto.getPrice()
        );

        return new AppointmentDTO(appointmentRepository.save(newAppointment));
    }

    public AppointmentDTO getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(APPOINTMENT_ID, String.valueOf(id))
        );

        /* Checking if the player has access to this data. */
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (appointment.getPrivacy().equals(AppointmentPrivacy.PRIVATE)
                && !groupService.getPlayersEmails(appointment.getGroup()).contains(email))
            throw new UnauthorizedException("You're not a member of the group!");

        return new AppointmentDTO(appointment);
    }

    public Appointment addPlayerToAppointment(Appointment appointment, Player player) {
        try {
            if (appointment.getPlayers().add(player)) {
                appointment.setOccupancy(appointment.getOccupancy() + 1);
                return appointmentRepository.save(appointment);
            } else {
                throw new CustomizableBadRequestException("You are already attending this appointment!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerErrorException("Something went wrong on the server!");
        }
    }
    
    public Appointment removePlayerFromAppointment(Appointment appointment, Player player) {
        if (appointment.getPlayers().remove(player)) {
            appointment.setOccupancy(appointment.getOccupancy() - 1);
            return appointmentRepository.save(appointment);
        } else {
            throw new CustomizableBadRequestException("You are not attending this appointment!");
        }
    }

    @Transactional
    public GameBasicInfoDTO addGame(CreateGameDTO dto) {
        if (!validateCreateGameDTO(dto))
            throw new InvalidInputDataException("Invalid input of data!");

        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId()).orElseThrow(
                () -> new ResourceNotFoundException(APPOINTMENT_ID, String.valueOf(dto.getAppointmentId()))
        );

        if (!playerService.doPlayersExist(Stream.concat(dto.getTeamRed().stream(), dto.getTeamBlue().stream()).toList()))
            throw new CustomizableBadRequestException("You entered one or more non-existing players!");

        String loggedInPlayersEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Player loggedInPlayer;
        try {
            loggedInPlayer = playerService.getPlayerByEmail(loggedInPlayersEmail);
        } catch (Exception e) {
            throw new UnauthorizedException("Unauthorized");
        }

        if (!appointment.getPlayers().contains(loggedInPlayer))
            throw new UnauthorizedException("You are not an attendee of this appointment!");

        Set<Player> teamRedPlayers = new HashSet<>();
        dto.getTeamRed().forEach(email -> {
            /* Getting each player by his email and adding him to the Set of players,
             * which will later be used to form a Team (teamRed) and that Team
             * will then be added to the Game which will be saved in the database. */
            Player player = playerService.getPlayerByEmail(email);
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
            Player player = playerService.getPlayerByEmail(email);
            teamBluePlayers.add(player);

            // Updating each player's number of games and saving it to the database.
            player.setNumberOfGames(player.getNumberOfGames() + 1);
            playerRepository.save(player);
        });

        // Initializing a new game - adding teams to it and associating it with the appointment.
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
            Player player = playerService.getPlayerByEmail(goalDTO.getPlayer());
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
        return new GameBasicInfoDTO(gameRepository.save(game));
    }

    public List<AppointmentDTO> getAllPublicAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        List<AppointmentDTO> publicAppointmentsDTO = new ArrayList<>();

        appointments.forEach(appointment -> {
            if (appointment.getPrivacy().equals(AppointmentPrivacy.PUBLIC))
                publicAppointmentsDTO.add(appointmentMapper.fromEntityToDto(appointment));
        });

        return publicAppointmentsDTO;
    }

    public AppointmentDTO attendAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(APPOINTMENT_ID, String.valueOf(id))
        );

        /* Checking if the player can attend this appointment. */
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (appointment.getPrivacy().equals(AppointmentPrivacy.PRIVATE)
                && !groupService.getPlayersEmails(appointment.getGroup()).contains(email))
            throw new UnauthorizedException("You're not a member of the group!");

        if (appointment.getOccupancy() >= appointment.getCapacity())
            throw new CustomizableBadRequestException("Full capacity!");

        Player player = playerService.getPlayerByEmail(email);

        return new AppointmentDTO(addPlayerToAppointment(appointment, player));
    }

    public AppointmentDTO cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(APPOINTMENT_ID, String.valueOf(id))
        );

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Player player = playerService.getPlayerByEmail(email);

        return new AppointmentDTO(removePlayerFromAppointment(appointment, player));
    }

    public boolean amIattending(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(
                () -> new ResourceNotFoundException(APPOINTMENT_ID, String.valueOf(appointmentId))
        );

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (Player player : appointment.getPlayers()) {
            if (player.getEmail().equals(email))
                return true;
        }

        return false;
    }

    public Set<AppointmentDTO> getGroupsAppointments(Long groupId) {
        Group group = groupService.getGroupById(groupId);

        Set<AppointmentDTO> appointmentSet = new HashSet<>();
        group.getAppointments().forEach(appointment -> {
            appointmentSet.add(new AppointmentDTO(appointment));
        });

        return appointmentSet;
    }
}
