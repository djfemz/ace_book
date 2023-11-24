package africa.semicolon.acebook.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequest {
    private String senderEmail;
    private String recipientEmail;
    private String message;
}
