package africa.semicolon.acebook.security;


import africa.semicolon.acebook.security.filters.AceBookAuthenticationFilter;
import africa.semicolon.acebook.security.filters.JwtAuthorizationFilter;
import africa.semicolon.acebook.security.services.JwtService;
import africa.semicolon.acebook.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static org.springframework.http.HttpMethod.POST;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        var authenticationFilter = new AceBookAuthenticationFilter(authenticationManager, jwtService);
        authenticationFilter.setFilterProcessesUrl("/api/v1/login");
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(c->c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthorizationFilter(jwtService), authenticationFilter.getClass())
                .authorizeHttpRequests(c->c.requestMatchers(POST,"/api/v1/login", "/api/v1/account")
                        .permitAll()
                        .anyRequest().authenticated()).build();
    }




}

