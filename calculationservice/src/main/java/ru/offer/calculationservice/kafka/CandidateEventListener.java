package ru.offer.calculationservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import ru.offer.calculationservice.consumer.CalculationCompletedEvent;
import ru.offer.calculationservice.service.CalculationService;
import ru.offer.candidate.dto.CandidateCreatedEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class CandidateEventListener {
    private final RedisTemplate<String, String> redisTemplate;

    private final CalculationService calculationService;

    @KafkaListener(topics = "candidate-created", groupId = "calculation-service-group")
    public void handleCandidateCreated(CandidateCreatedEvent event) {
        log.info("Received CandidateCreatedEvent: {}", event);

        if (redisTemplate.hasKey("calculated:" + event.id())) {
            log.info("Candidate {} already processed", event.id());
            return;
        }

        CalculationCompletedEvent result = calculationService.calculate(event);

        calculationService.publishCalculationCompleted(result);
    }
}
