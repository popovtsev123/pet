package ru.offer.candidate.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.offer.candidate.dto.CandidateCreatedEvent;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaEventPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "candidate-created";

    public void publish(CandidateCreatedEvent event) {
        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send(TOPIC, event.id().toString(), event);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Event published: offset={}, partition={}",
                        result.getRecordMetadata().offset(),
                        result.getRecordMetadata().partition());
            } else {
                log.error("Failed to publish event for candidate id={}", event.id(), ex);
            }
        });
    }
}
