package africa.semicolon.acebook.services;

import africa.semicolon.acebook.config.PaymentConfig;
import africa.semicolon.acebook.dtos.request.CreatePaymentRequest;
import africa.semicolon.acebook.dtos.response.CreatePaymentResponse;
import africa.semicolon.acebook.dtos.response.PaystackTransactionVerificationResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Objects;

import static africa.semicolon.acebook.utils.AppUtils.JWT_PREFIX;
import static java.net.URI.create;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@Primary
@AllArgsConstructor
public class PaystackPaymentService implements PaymentService {

    private final PaymentConfig paymentConfig;
    private final RestTemplate restTemplate;

    @Override
    public CreatePaymentResponse<?> pay(CreatePaymentRequest paymentRequest) {
        String apiKey = paymentConfig.getPaystackApiKey();
        URI uri = create(paymentConfig.getPaystackUrl());
        RequestEntity<CreatePaymentRequest> data = buildPaymentRequest(paymentRequest, apiKey, uri);
        var response = restTemplate.postForEntity(uri, data, CreatePaymentResponse.class);
        return response.getBody();
    }

    @Override
    public String verifyPaymentFor(String transactionReference) {
        String url =paymentConfig.getPaystackVerificationUrl()+transactionReference;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(AUTHORIZATION, JWT_PREFIX.concat(paymentConfig.getPaystackApiKey()));
        RequestEntity<?> request = new RequestEntity<>(httpHeaders, GET, null);
        var response = restTemplate.exchange(url, GET, request ,PaystackTransactionVerificationResponse.class);
        return Objects.requireNonNull(response.getBody()).getMessage();
    }

    private static RequestEntity<CreatePaymentRequest> buildPaymentRequest(CreatePaymentRequest paymentRequest, String apiKey, URI uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, JWT_PREFIX.concat(apiKey));
        headers.setContentType(APPLICATION_JSON);
        RequestEntity<CreatePaymentRequest> data =
                new RequestEntity<>(paymentRequest, headers, POST, uri);
        return data;
    }
}
