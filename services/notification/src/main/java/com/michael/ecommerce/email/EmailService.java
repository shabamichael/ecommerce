package com.michael.ecommerce.email;

import com.michael.ecommerce.kafka.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.michael.ecommerce.email.EmailTemplates.ORDER_CONFIRMATION;
import static com.michael.ecommerce.email.EmailTemplates.PAYMENT_CONFIRMATION;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentSuccessEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderRefence) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper =
                new MimeMessageHelper(mimeMessage, MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        mimeMessageHelper.setTo(destinationEmail);
        mimeMessageHelper.setFrom("shabamichael@outlook.com");
        final String templateName = PAYMENT_CONFIRMATION.getTemplateName();
        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("amount", amount);
        variables.put("orderReference", orderRefence);

        Context context = new Context();
        context.setVariables(variables);
        mimeMessageHelper.setSubject(PAYMENT_CONFIRMATION.getSubject());

        try{
        String htmlTemplate = templateEngine.process(templateName, context);
        mimeMessageHelper.setText(htmlTemplate, true);
        mimeMessageHelper.setTo(destinationEmail);
        mailSender.send(mimeMessage);
        log.info(String.format("INFO - Email successfully sent to %s with template %s", destinationEmail, templateName));
        } catch (Exception e) {
            log.error(String.format("ERROR - Email not sent to %s with template %s", destinationEmail, templateName));
            throw new MessagingException("Error while sending email", e);
        }
    }

    @Async
    public void sendOrderConfirmationEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderRefence,
            List<Product>productList) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper =
                new MimeMessageHelper(mimeMessage, MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        mimeMessageHelper.setTo(destinationEmail);
        mimeMessageHelper.setFrom("shabamichael@outlook.com");
        final String templateName = ORDER_CONFIRMATION.getTemplateName();
        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("totalAmount", amount);
        variables.put("orderReference", orderRefence);
        variables.put("products", productList);

        Context context = new Context();
        context.setVariables(variables);
        mimeMessageHelper.setSubject(ORDER_CONFIRMATION.getSubject());

        try{
            String htmlTemplate = templateEngine.process(templateName, context);
            mimeMessageHelper.setText(htmlTemplate, true);
            mimeMessageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info(String.format("INFO - Email successfully sent to %s with template %s", destinationEmail, templateName));
        } catch (Exception e) {
            log.error(String.format("ERROR - Email not sent to %s with template %s", destinationEmail, templateName));
            throw new MessagingException("Error while sending email", e);
        }
    }
}
