package africa.semicolon.acebook.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddFriendRequest {
    private Long sender;
    private Long recipient;
    private String message;
}
