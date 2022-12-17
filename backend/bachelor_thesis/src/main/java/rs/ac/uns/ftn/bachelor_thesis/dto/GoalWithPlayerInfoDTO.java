package rs.ac.uns.ftn.bachelor_thesis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoalWithPlayerInfoDTO {
    private Long id;
    private Long gameId;
    private String teamColor;
    private PlayerDTO player;
}
