package africa.semicolon.acebook.services;


import africa.semicolon.acebook.dtos.request.Recipient;
import africa.semicolon.acebook.dtos.request.SendMailRequest;
import africa.semicolon.acebook.dtos.request.Sender;
import africa.semicolon.acebook.dtos.response.SendMailResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Slf4j
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void testSendMail(){
        SendMailRequest mailRequest = buildMailRequest();
        SendMailResponse response = mailService.sendMail(mailRequest);
        assertNotNull(response);
        assertEquals(201, response.getStatusCode());
    }

    private static SendMailRequest buildMailRequest() {
        //1. Create mail sending request
        SendMailRequest mailRequest = new SendMailRequest();
        //2. Create Sender
        Sender sender = new Sender("acebook", "acebook@semicolon.africa");
        //3. Create Recipient Collection
        List<Recipient> recipients = List.of(
                new Recipient("moyin", "udeheddie01@hotmail.com")
        );
        mailRequest.setSubject("testing 1,2,3...");
        mailRequest.setHtmlContent("<p>Hello Semicolon</p>");
        mailRequest.setSender(sender);
        mailRequest.setRecipients(recipients);
        return mailRequest;
    }
}
