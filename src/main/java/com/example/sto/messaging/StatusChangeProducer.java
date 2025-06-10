package com.example.sto.messaging;

import com.example.sto.domain.events.StatusChangeEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatusChangeProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${spring.kafka.topics.status-change}")
    private String statusChangeTopic;

    public void publishStatusChangeEvent(StatusChangeEvent event) {
        try {
            String eventJson = objectMapper.writeValueAsString(event);
            String key = event.getServiceRequestId().toString();

            CompletableFuture<SendResult<String, String>> future =
                    kafkaTemplate.send(statusChangeTopic, key, eventJson);

            future.thenAccept(result ->
                            log.info("Status change event published successfully for request: {}",
                                    event.getRequestNumber()))
                    .exceptionally(ex -> {
                        log.error("Failed to publish status change event for request: {}",
                                event.getRequestNumber(), ex);
                        return null;
                    });

        } catch (JsonProcessingException e) {
            log.error("Failed to serialize status change event", e);
        }
    }
}
