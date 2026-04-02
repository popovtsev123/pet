package ru.offer.calculationservice.consumer;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CalculationCompletedEvent {
    private Long candidateId;
    private BigDecimal recommendedSalary;
    private double experienceCoefficient;
    private double levelCoefficient;
    private double marketCoefficient;
}
