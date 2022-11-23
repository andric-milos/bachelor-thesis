package rs.ac.uns.ftn.bachelor_thesis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterInfoDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String telephone;
    private String role; // whitelisted values are "manager" and "player"
}
