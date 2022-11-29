package rs.ac.uns.ftn.bachelor_thesis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.bachelor_thesis.model.Player;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerWithStatisticsDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
    private int numberOfGoals;
    private int numberOfGames;
    private int numberOfGamesWon;
    private double winningPercentage;
    private double goalsPerAppointment;
    private int canceledJustBefore;

    public PlayerWithStatisticsDTO(Player player) {
        this.id = player.getId();
        this.firstName = player.getFirstName();
        this.lastName = player.getLastName();
        this.email = player.getEmail();
        this.telephone = player.getTelephone();
        this.numberOfGoals = player.getNumberOfGoals();
        this.numberOfGames = player.getNumberOfGames();
        this.numberOfGamesWon = player.getNumberOfGamesWon();
        this.winningPercentage = player.getWinningPercentage();
        this.goalsPerAppointment = player.getGoalsPerAppointment();
        this.canceledJustBefore = player.getCanceledJustBefore();
    }
}
