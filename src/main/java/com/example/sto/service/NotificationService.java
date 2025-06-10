package com.example.sto.service;

import com.example.sto.domain.events.StatusChangeEvent;

public interface NotificationService {
    void sendStatusChangeNotification(StatusChangeEvent event);
    void sendSmsNotification(String phoneNumber, String message);
    void sendEmailNotification(String email, String subject, String message);
}
