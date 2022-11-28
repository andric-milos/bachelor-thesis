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
public class PlayerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;

    public PlayerDTO(Player player) {
        this.id = player.getId();
        this.firstName = player.getFirstName();
        this.lastName = player.getLastName();
        this.email = player.getEmail();
        this.telephone = player.getTelephone();
    }
}
