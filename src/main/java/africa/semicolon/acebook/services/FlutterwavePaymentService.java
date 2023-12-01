package africa.semicolon.acebook.services;

import africa.semicolon.acebook.dtos.request.CreatePaymentRequest;
import africa.semicolon.acebook.dtos.response.CreatePaymentResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;


@Service
public class FlutterwavePaymentService implements PaymentService{
    @Override
    public CreatePaymentResponse pay(CreatePaymentRequest paymentRequest) {
        RestTemplate restTemplate = new RestTemplate();

        URI uri = URI.create("<FLUTTERWAVE_URL>");
        String apiKey = "<API_KEY>";
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, "Bearer ".concat(apiKey));
        headers.setContentType(APPLICATION_JSON);
        RequestEntity<CreatePaymentRequest> data =
                new RequestEntity<>(paymentRequest, headers, POST, uri);
        var response =
                restTemplate.postForEntity(uri, data, CreatePaymentResponse.class);
        return response.getBody();
    }

    @Override
    public String verifyPaymentFor(String transactionReference) {
        return null;
    }
}
