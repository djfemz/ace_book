package africa.semicolon.acebook.services;

import africa.semicolon.acebook.dtos.request.NotificationRequest;
import africa.semicolon.acebook.dtos.response.NotificationResponse;
import africa.semicolon.acebook.dtos.response.SendNotificationResponse;

import java.util.List;

public interface NotificationService {
    SendNotificationResponse send(NotificationRequest notificationRequest);


    List<NotificationResponse> getBySender(String email);
}
