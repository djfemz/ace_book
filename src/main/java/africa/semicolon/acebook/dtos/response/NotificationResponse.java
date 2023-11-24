package africa.semicolon.acebook.dtos.response;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NotificationResponse {
    private String senderEmail;
    private String recipientEmail;
    private String message;
}
