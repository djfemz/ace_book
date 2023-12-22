package africa.semicolon.acebook.security.filters;

import africa.semicolon.acebook.dtos.request.LoginRequest;
import africa.semicolon.acebook.dtos.response.LoginResponse;
import africa.semicolon.acebook.security.services.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static africa.semicolon.acebook.utils.AppUtils.ACCESS_TOKEN_FIELD_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RequiredArgsConstructor
@Slf4j
public class AceBookAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private ObjectMapper mapper = new ObjectMapper();

    private final JwtService jwtService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try (InputStream stream = request.getInputStream()){
            LoginRequest loginRequest = mapper.readValue(stream, LoginRequest.class);
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();

            log.info("username--->{} password--->{}", username, password);

            //1. create an Authentication object that is not yet authenticated
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
            //2. send the Authentication object from 1 to the AuthenticationManager
            //3. Get back the now authenticated Authentication object from the manager
            Authentication authenticationResult = authenticationManager.authenticate(authentication);
            log.info("auth result--> {}", authenticationResult);
            //4. Put the Authentication object from 3 in the SecurityContext
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authenticationResult);
            return authenticationResult;
        }catch (IOException exception){
            throw new BadCredentialsException("Authentication failed");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        LoginResponse loginResponse = new LoginResponse();
        String token = jwtService.generateTokenFor(authResult.getPrincipal().toString());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getOutputStream().write(mapper.writeValueAsBytes(Map.of(ACCESS_TOKEN_FIELD_VALUE, token)));
        response.flushBuffer();
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getOutputStream().flush();
    }
}
