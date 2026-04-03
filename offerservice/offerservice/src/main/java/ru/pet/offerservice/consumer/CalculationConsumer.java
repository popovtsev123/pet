package ru.pet.offerservice.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.pet.offerservice.dto.CalculationCompletedEvent;
import ru.pet.offerservice.service.OfferService;

@Slf4j
@Component
@RequiredArgsConstructor
public class CalculationConsumer {

    private final OfferService offerService;

    @KafkaListener(topics = "calculation-completed", groupId = "offer-service-group")
    public void consume(CalculationCompletedEvent event) {
        log.info("Received calculation-completed event for candidate {}", event.getCandidateId());
        offerService.processCalculation(event);
    }
}

