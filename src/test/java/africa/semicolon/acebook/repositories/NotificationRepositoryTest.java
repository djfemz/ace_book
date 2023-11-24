package africa.semicolon.acebook.repositories;

import africa.semicolon.acebook.models.Notification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    public void findBySenderTest() {
        String email = "test@email.com";
        List<Notification> notifications = notificationRepository.findBySender(email);

        Notification notification = new Notification();
        notification.setSenderEmail(email);
        notificationRepository.save(notification);

        assertThat(notificationRepository.findBySender(email).size())
                .isGreaterThan(notifications.size());
    }
}