package rs.ac.uns.ftn.bachelor_thesis.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Player extends User {
    private int numberOfGoals; /* Overall number of goals scored in all games in all appointments.
    Calculated: length/size of the set of goals. */
    private int numberOfGames; // Overall number of games played in all appointments.
    private int numberOfGamesWon;
    private double winningPercentage; // Calculated: numberOfGamesWon / numberOfGames
    private double goalsPerAppointment; // Calculated: numberOfGoals / appointments.length
    private int canceledJustBefore; /* Number of times a player has canceled within the
    12 hours time window before the start of the appointment. */

    @ManyToMany(mappedBy = "players")
    private Set<Appointment> appointments;

    @ManyToMany(mappedBy = "players")
    private Set<Team> teams;

    @ManyToMany(mappedBy = "players")
    private Set<Group> groups;

    @OneToMany(mappedBy = "player")
    private Set<Goal> goals;

    @OneToMany(mappedBy = "player")
    private Set<PlayerComment> comments;
}
