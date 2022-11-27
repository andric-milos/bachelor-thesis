package rs.ac.uns.ftn.bachelor_thesis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokensDTO {
    private String accessToken;
    private String refreshToken;
    private String subject;
    private String issuer;
    private String[] roles;
    private Long expiresIn;
}
