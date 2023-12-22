package africa.semicolon.acebook.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Getter
@Setter
public class Account  {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    @Embedded
    private AccountDetails accountDetails;
    @ManyToOne
    private Account friend;
    @Enumerated(STRING)
    private Tier tier;

}
