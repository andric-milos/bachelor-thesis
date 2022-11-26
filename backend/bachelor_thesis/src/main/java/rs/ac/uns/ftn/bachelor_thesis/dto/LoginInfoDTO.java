package rs.ac.uns.ftn.bachelor_thesis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginInfoDTO {
    private String email;
    private String password;
}
