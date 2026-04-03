package ru.offer.calculationservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CandidateCreatedEvent {
    private UUID eventId;
    private Long id;
    private String name;
    private String level;
    private Integer experienceYears;
    private BigDecimal currentSalary;
}
