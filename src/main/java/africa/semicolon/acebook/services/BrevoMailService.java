package africa.semicolon.acebook.services;

import africa.semicolon.acebook.config.AppConfig;
import africa.semicolon.acebook.dtos.request.SendMailRequest;
import africa.semicolon.acebook.dtos.response.SendMailResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Service
@AllArgsConstructor
public class BrevoMailService implements MailService{

    private final AppConfig appConfig;
    private final RestTemplate restTemplate;

    @Override
    public SendMailResponse sendMail(SendMailRequest request) {
        HttpHeaders headers = addRequestHeaders();
        RequestEntity<SendMailRequest> requestEntity = new RequestEntity<>(request, headers, POST, URI.create(appConfig.getMailServiceUrl()));
        ResponseEntity<SendMailResponse> mailResponse =restTemplate.postForEntity(appConfig.getMailServiceUrl(), requestEntity,SendMailResponse.class);
        return buildSendMailResponse(mailResponse);
    }

    private static SendMailResponse buildSendMailResponse(ResponseEntity<SendMailResponse> mailResponse) {
        HttpStatusCode code = mailResponse.getStatusCode();
        var response = mailResponse.getBody();
        if (response==null) throw new RuntimeException("Mail Sending failed");
        response.setStatusCode(code.value());
        return response;
    }

    private HttpHeaders addRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        headers.setAccept(List.of(APPLICATION_JSON));
        headers.set("api-key", appConfig.getMailApiKey());
        return headers;
    }
}
