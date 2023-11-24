package africa.semicolon.acebook.dtos.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateAccountRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
