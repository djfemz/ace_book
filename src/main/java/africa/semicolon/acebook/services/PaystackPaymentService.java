package africa.semicolon.acebook.services;

import africa.semicolon.acebook.config.AppConfig;
import africa.semicolon.acebook.config.PaymentConfig;
import africa.semicolon.acebook.dtos.request.CreatePaymentRequest;
import africa.semicolon.acebook.dtos.response.CreatePaymentResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@Primary
@AllArgsConstructor
public class PaystackPaymentService implements PaymentService {

    private final PaymentConfig paymentConfig;
    @Override
    public CreatePaymentResponse<?> pay(CreatePaymentRequest paymentRequest) {
        String apiKey = paymentConfig.getPaystackApiKey();
        RestTemplate restTemplate = new RestTemplate();
        URI uri = URI.create(paymentConfig.getPaystackUrl());
        RequestEntity<CreatePaymentRequest> data = buildPaymentRequest(paymentRequest, apiKey, uri);
        var response = restTemplate.postForEntity(uri, data, CreatePaymentResponse.class);
        return response.getBody();
    }

    private static RequestEntity<CreatePaymentRequest> buildPaymentRequest(CreatePaymentRequest paymentRequest, String apiKey, URI uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, "Bearer ".concat(apiKey));
        headers.setContentType(APPLICATION_JSON);
        RequestEntity<CreatePaymentRequest> data =
                new RequestEntity<>(paymentRequest, headers, POST, uri);
        return data;
    }
}
