package ru.offer.candidate.dto;

import java.math.BigDecimal;

public record CandidateCreatedEvent(
        Long id,
        String name,
        String level,
        Integer experienceYears,
        BigDecimal currentSalary
) {}
