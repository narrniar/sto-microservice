package com.example.sto.service.implementation;

import com.example.sto.domain.events.StatusChangeEvent;
import com.example.sto.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {


    @Override
    @Async
    public void sendStatusChangeNotification(StatusChangeEvent event) {
        String message = buildNotificationMessage(event);

        // Mock SMS notification
        sendSmsNotification(event.getClientPhone(), message);

        // Mock Email notification
        String subject = String.format("Service Request %s Status Update", event.getRequestNumber());
        sendEmailNotification(event.getClientEmail(), subject, message);
    }

    @Override
    public void sendSmsNotification(String phoneNumber, String message) {
        // Mock SMS service - in real implementation, integrate with SMS provider
        log.info("\u001B[33mSMS Notification sent to {}: {}\u001B[0m", phoneNumber, message);
        

        
        // Simulate processing time
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void sendEmailNotification(String email, String subject, String message) {
        // Mock Email service - in real implementation, integrate with email provider
        log.info("\u001B[33mEmail Notification sent to {} with subject '{}': {}\u001B[0m", email, subject, message);
        


        
        // Simulate processing time
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private String buildNotificationMessage(StatusChangeEvent event) {
        return String.format(
                "Your service request %s has been updated from %s to %s. %s",
                event.getRequestNumber(),
                event.getFromStatus(),
                event.getToStatus(),
                event.getReason() != null ? "Reason: " + event.getReason() : ""
        );
    }
}

