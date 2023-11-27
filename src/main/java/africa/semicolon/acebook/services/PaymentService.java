package africa.semicolon.acebook.services;

import africa.semicolon.acebook.dtos.request.CreatePaymentRequest;
import africa.semicolon.acebook.dtos.response.CreatePaymentResponse;

public interface PaymentService {
    CreatePaymentResponse<?> pay(CreatePaymentRequest paymentRequest);
}
