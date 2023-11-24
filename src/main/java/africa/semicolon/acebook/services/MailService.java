package africa.semicolon.acebook.services;

import africa.semicolon.acebook.dtos.request.SendMailRequest;
import africa.semicolon.acebook.dtos.response.SendMailResponse;

public interface MailService {
    SendMailResponse sendMail(SendMailRequest request);
}
