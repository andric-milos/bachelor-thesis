package rs.ac.uns.ftn.bachelor_thesis.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
public class TokenUtil {
    public final static String SECRET = "kjkszpj";
    public final static Algorithm ALGORITHM = Algorithm.HMAC256(SECRET.getBytes());
    public final static int ACCESS_TOKEN_MINUTES = 10;
    public final static int REFRESH_TOKEN_MINUTES = 30;

    public String generateAccessToken(String subject, String issuer, List<String> authorities) {
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_MINUTES * 60 * 1000))
                .withIssuer(issuer)
                .withClaim("roles", authorities)
                .sign(ALGORITHM);
    }

    public String generateRefreshToken(String subject, String issuer) {
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_MINUTES * 60 * 1000))
                .withIssuer(issuer)
                .sign(ALGORITHM);
    }

    public DecodedJWT verify(String token) {
        JWTVerifier verifier = JWT.require(ALGORITHM).build();
        return verifier.verify(token);
    }
}
