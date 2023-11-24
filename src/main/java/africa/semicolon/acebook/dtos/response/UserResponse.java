package africa.semicolon.acebook.dtos.response;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserResponse {
    private String email;
    private String firstname;
    private String lastname;

}
