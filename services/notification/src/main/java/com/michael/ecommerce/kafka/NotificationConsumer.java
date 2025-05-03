package com.michael.ecommerce.kafka;


import com.michael.ecommerce.email.EmailService;
import com.michael.ecommerce.kafka.payment.PaymentConfirmation;
import com.michael.ecommerce.notification.NotifactionType;
import com.michael.ecommerce.notification.Notification;
import com.michael.ecommerce.notification.NotificationRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info(String.format("Consuming the message from payment topic:: %s", paymentConfirmation));
        log.info("Received Payment Confirmation: {}", paymentConfirmation);
        // Save the payment confirmation to the database
        notificationRepository.save(
                Notification.builder()
                        .type(NotifactionType.PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()

        );

        // send email payment confirmation

        emailService.sendPaymentSuccessEmail(
                paymentConfirmation.customerEmail(),
                paymentConfirmation.customerFirstName() + " " + paymentConfirmation.customerLastName(),
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info(String.format("Consuming the message from order topic:: %s", orderConfirmation));
        log.info("Received ordder Confirmation: {}", orderConfirmation);
        // Save the order confirmation to the database
        notificationRepository.save(
                Notification.builder()
                        .type(NotifactionType.ORDER_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()

        );


        // send email Order confirmation
        emailService.sendOrderConfirmationEmail(
                orderConfirmation.customer().email(),
                orderConfirmation.customer().firstName() + " " + orderConfirmation.customer().lastName(),
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );
    }
}
