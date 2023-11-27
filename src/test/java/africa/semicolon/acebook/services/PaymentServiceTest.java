package africa.semicolon.acebook.services;


import africa.semicolon.acebook.dtos.request.CreatePaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    @Test
    public void testPay(){
        CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest();
        createPaymentRequest.setAmount(TEN);
        createPaymentRequest.setEmail("jon@email.com");

        var response = paymentService.pay(createPaymentRequest);
        log.info("payment response---->{}", response);
        assertThat(response).isNotNull();
    }
}
