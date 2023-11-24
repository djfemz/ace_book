package africa.semicolon.acebook.services;

import africa.semicolon.acebook.dtos.request.NotificationRequest;
import africa.semicolon.acebook.dtos.response.SendNotificationResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.not;

@SpringBootTest
@Slf4j
public class NotificationServiceTest {
    @Autowired
    private NotificationService notificationService;

    @Test
    public void sendNotificationTest(){
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setSenderEmail("john@email.com");
        notificationRequest.setRecipientEmail("jane@email.com");
        notificationRequest.setMessage("Hello 101");
        SendNotificationResponse response = notificationService.send(notificationRequest);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isNotNull();

    }

    @Test
    @Sql(scripts = {"/db/data.sql"})
    public void getNotificationTest(){
        var notification = notificationService.getBySender("john@email.com");
        log.info("notification:: {}", notification);
        assertThat(notification).isNotNull();
    }
}
