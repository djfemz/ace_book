package africa.semicolon.acebook.models;


import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Setter
@Getter
@ToString
public class AccountDetails {
    private String email;
    private String password;
}
