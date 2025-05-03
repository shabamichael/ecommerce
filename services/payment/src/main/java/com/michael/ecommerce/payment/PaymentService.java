package com.michael.ecommerce.payment;

import com.michael.ecommerce.notification.NotificationProducer;
import com.michael.ecommerce.notification.PaymentNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PaymentService {

    private final NotificationProducer notificationProducer;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public Integer createPayment(PaymentRequest paymentRequest) {
        var payment = paymentRepository.save(paymentMapper.toPayment(paymentRequest));

        //Send Notifications to Notification Microservices
        //Setup Kafka , add the properties
        notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        paymentRequest.orderReference(),
                        paymentRequest.amount().toString(),
                        paymentRequest.paymentMethod(),
                        paymentRequest.customer().email(),
                        paymentRequest.customer().firstName(),
                        paymentRequest.customer().lastName()
                )
        );
        return null;
    }
}
