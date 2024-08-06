package rs.ac.uns.ftn.bachelor_thesis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TokensDTO {
    private String accessToken;
    private String refreshToken;
    private String subject;
    private String issuer;
    private String[] roles;
    private Long expiresIn;
}
