package africa.semicolon.acebook.dtos.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddMediaResponse {
    private String message;
    private Long mediaId;
    private String url;
}
