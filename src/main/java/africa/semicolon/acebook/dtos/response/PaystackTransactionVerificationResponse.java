package africa.semicolon.acebook.dtos.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaystackTransactionVerificationResponse {
    private String status;
    private String message;
}
