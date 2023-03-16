package rs.ac.uns.ftn.bachelor_thesis.dto;

import lombok.*;
import rs.ac.uns.ftn.bachelor_thesis.enumeration.TeamColor;
import rs.ac.uns.ftn.bachelor_thesis.model.Appointment;
import rs.ac.uns.ftn.bachelor_thesis.model.Game;
import rs.ac.uns.ftn.bachelor_thesis.model.Goal;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AppointmentDTO {
    private Long id;
    private Long date;
    private String privacy;
    private int capacity;
    private int occupancy;
    private double price;
    private LocationDTO location;
    private Long groupId;
    private String groupName;
    private List<String> playersEmails = new ArrayList<>();
    private List<GameBasicInfoDTO> games = new ArrayList<>();

    public AppointmentDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.date = appointment.getDate().getTime();
        this.privacy = appointment.getPrivacy().toString().toLowerCase();
        this.capacity = appointment.getCapacity();
        this.occupancy = appointment.getOccupancy();
        this.price = appointment.getPrice();

        this.location = new LocationDTO(
                appointment.getLocation().getId(),
                appointment.getLocation().getAddress(),
                appointment.getLocation().getLongitude(),
                appointment.getLocation().getLatitude()
        );

        this.groupId = appointment.getGroup().getId();
        this.groupName = appointment.getGroup().getName();

        if (appointment.getPlayers() != null)
            appointment.getPlayers().forEach(player -> this.playersEmails.add(player.getEmail()));

        if (appointment.getGames() != null) {
            int goalsByTeamRed, goalsByTeamBlue;
            for (Game game : appointment.getGames()) {
                goalsByTeamRed = 0;
                goalsByTeamBlue = 0;

                for (Goal goal : game.getGoals()) {
                    if (goal.getTeamColor().equals(TeamColor.BLUE))
                        ++goalsByTeamBlue;
                    else if (goal.getTeamColor().equals(TeamColor.RED))
                        ++goalsByTeamRed;
                }

                this.games.add(new GameBasicInfoDTO(game.getId(), goalsByTeamRed, goalsByTeamBlue));
            }
        }
    }
}
