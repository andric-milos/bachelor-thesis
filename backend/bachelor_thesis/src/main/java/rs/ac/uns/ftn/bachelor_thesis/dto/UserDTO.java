package rs.ac.uns.ftn.bachelor_thesis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.bachelor_thesis.model.User;

import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String telephone;
    private HashSet<String> roles = new HashSet<>();

    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.telephone = user.getTelephone();
        user.getRoles().forEach(role -> {
            this.roles.add(role.getName());
        });
    }
}
