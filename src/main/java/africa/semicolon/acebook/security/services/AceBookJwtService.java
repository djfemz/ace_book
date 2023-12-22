package africa.semicolon.acebook.security.services;

import africa.semicolon.acebook.security.JwtConfig;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static java.time.ZoneOffset.UTC;


@Service
@AllArgsConstructor
//TODO: remove hardcoded values
public class AceBookJwtService implements JwtService{

    private final JwtConfig jwtConfig;
    private final UserDetailsService userDetailsService;
    @Override
    public String generateTokenFor(String username) {
        return JWT.create()
                  .withSubject("access_token")
                  .withClaim("username", username)
                  .withExpiresAt(LocalDateTime.now().plusDays(jwtConfig.getJwtDuration())
                          .toInstant(UTC))
                  .sign(HMAC512(jwtConfig.getJwtSecret().getBytes()));
    }

    @Override
    public boolean validate(String token) {
        Algorithm algorithm = HMAC512(jwtConfig.getJwtSecret().getBytes());
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .build().verify(token);
        return isValidToken(decodedJWT);
    }

    @Override
    public UserDetails extractUserDetailsFrom(String token) {
        Algorithm algorithm = HMAC512(jwtConfig.getJwtSecret().getBytes());
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .build().verify(token);
        String username =  decodedJWT.getClaim("username").as(String.class);
        return userDetailsService.loadUserByUsername(username);
    }

    private static boolean isValidToken(DecodedJWT decodedJWT) {
        return isTokenNotExpired(decodedJWT) && isTokenWithValidIssuer(decodedJWT);
    }


    private static boolean isTokenWithValidIssuer(DecodedJWT decodedJWT) {
        return decodedJWT.getIssuer().equals("sync");
    }

    private static boolean isTokenNotExpired(DecodedJWT decodedJWT) {
        return Instant.now().isAfter(decodedJWT.getExpiresAtAsInstant());
    }



}
