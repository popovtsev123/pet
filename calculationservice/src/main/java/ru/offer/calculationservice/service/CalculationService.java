package ru.offer.calculationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.offer.calculationservice.consumer.CalculationCompletedEvent;
import ru.offer.calculationservice.dto.CandidateCreatedEvent;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CalculationService {
    private final RedisTemplate<String, Object> redisTemplate;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final String TOPIC_CALCULATION_COMPLETED = "calculation-completed";

    private static final Map<String, Double> coefficients = new HashMap<>();
    static {
        coefficients.put("Junior", 0.8);
        coefficients.put("Middle", 1.2);
        coefficients.put("Senior", 1.4);
    }

    private final double EXP_COEFFICIENT = 0.05;

    private final double JOB_COEFFICIENT_MARKET = 1.1;

    private final int ABS_MULTIPLY = 1;

    public CalculationCompletedEvent calculate(CandidateCreatedEvent candidateCreatedEvent) {
        String level = candidateCreatedEvent.getLevel();
        double levelCoefficient = coefficients.get(level);
        double experienceCoefficient = ABS_MULTIPLY + candidateCreatedEvent.getExperienceYears() * EXP_COEFFICIENT;

        BigDecimal recommendedSalary = candidateCreatedEvent.getCurrentSalary().
                multiply(BigDecimal.valueOf(levelCoefficient * experienceCoefficient * JOB_COEFFICIENT_MARKET));

        redisTemplate.opsForValue().set("calculated:" + candidateCreatedEvent.getId(), recommendedSalary);

        return new CalculationCompletedEvent(
                candidateCreatedEvent.getId(),
                recommendedSalary,
                experienceCoefficient,
                levelCoefficient,
                JOB_COEFFICIENT_MARKET
        );
    }

    public void publishCalculationCompleted(CalculationCompletedEvent event) {
        kafkaTemplate.send(TOPIC_CALCULATION_COMPLETED, event.getCandidateId().toString(), event);
    }
}
