package rs.ac.uns.ftn.bachelor_thesis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.bachelor_thesis.enumeration.TeamColor;
import rs.ac.uns.ftn.bachelor_thesis.model.Game;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameBasicInfoDTO {
    private Long id;
    private int goalsTeamRed;
    private int goalsTeamBlue;

    public GameBasicInfoDTO(Game game) {
        this.id = game.getId();
        this.goalsTeamRed = (int) game.getGoals().stream().filter(goal -> goal.getTeamColor().equals(TeamColor.RED)).count();
        this.goalsTeamBlue = (int) game.getGoals().stream().filter(goal -> goal.getTeamColor().equals(TeamColor.BLUE)).count();
    }
}
