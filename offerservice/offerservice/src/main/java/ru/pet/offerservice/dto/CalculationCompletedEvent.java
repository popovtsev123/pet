package ru.pet.offerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculationCompletedEvent {
    private Long candidateId;
    private BigDecimal recommendedSalary;
    private Double experienceCoefficient;
    private Double levelCoefficient;
    private Double marketCoefficient;
}
