package rs.ac.uns.ftn.bachelor_thesis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateGameDTO {
    private Long appointmentId;
    private List<String> teamRed; // List of players' emails.
    private List<String> teamBlue; // List of players' emails.
    private List<GoalDTO> goals; // Player's email & team color for each goal.
}
