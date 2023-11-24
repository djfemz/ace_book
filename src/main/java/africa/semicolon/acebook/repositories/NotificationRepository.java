package africa.semicolon.acebook.repositories;

import africa.semicolon.acebook.dtos.response.NotificationResponse;
import africa.semicolon.acebook.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
   @Query("SELECT n from Notification n where n.senderEmail=:email")
    List<Notification> findBySender(String email);
}
