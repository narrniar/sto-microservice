package com.example.sto.messaging;

import com.example.sto.domain.events.StatusChangeEvent;
import com.example.sto.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatusChangeConsumer {

    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${spring.kafka.topics.status-change}")
    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 1000, multiplier = 2.0),
            dltStrategy = org.springframework.kafka.retrytopic.DltStrategy.FAIL_ON_ERROR
    )
    public void handleStatusChangeEvent(
            @Payload String eventJson,
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            Acknowledgment acknowledgment) {

        try {
            log.debug("Received message: {}", eventJson);
            StatusChangeEvent event = objectMapper.readValue(eventJson, StatusChangeEvent.class);
            log.info("Processing status change event for request: {}", event.getRequestNumber());

            // Send notifications
            notificationService.sendStatusChangeNotification(event);

            log.info("Status change event processed successfully for request: {}",
                    event.getRequestNumber());
            
            // Acknowledge message after successful processing
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("Failed to process status change event with key: {}", key, e);
            // Do not acknowledge on error - will be retried
            // If we want to skip this message after retries are exhausted:
            // acknowledgment.acknowledge();
            throw new RuntimeException("Failed to process status change event", e);
        }
    }
}
