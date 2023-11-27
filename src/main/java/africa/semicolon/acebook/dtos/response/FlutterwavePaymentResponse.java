package africa.semicolon.acebook.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class FlutterwavePaymentResponse {
    @JsonProperty("phone_number")
    private String phoneNumber;
    private BigDecimal amount;
    private String network;
    @JsonProperty("flw_ref")
    private String flutterwaveReference;
    @JsonProperty("tx_ref")
    private String transactionReference;
    private String reference;
}
