package africa.semicolon.acebook.services;

import africa.semicolon.acebook.dtos.request.NotificationRequest;
import africa.semicolon.acebook.dtos.response.NotificationResponse;
import africa.semicolon.acebook.dtos.response.SendNotificationResponse;
import africa.semicolon.acebook.models.Notification;
import africa.semicolon.acebook.repositories.NotificationRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class AceBookNotificationService implements NotificationService{
    private final NotificationRepository notificationRepository;
    private final ModelMapper modelMapper;
    @Override
    public SendNotificationResponse send(NotificationRequest notificationRequest) {
        Notification notification =  modelMapper.map(notificationRequest, Notification.class);
        notificationRepository.save(notification);
        return new SendNotificationResponse("notification sent");
    }

    @Override
    public List<NotificationResponse> getBySender(String email) {
         List<Notification> notifications = notificationRepository.findBySender(email);
         return notifications.stream()
                .map(notification -> modelMapper.map(notification, NotificationResponse.class))
                .toList();
    }
}
