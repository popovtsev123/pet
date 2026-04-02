package ru.offer.candidate.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CandidateCreatedEvent(
        UUID eventId,
        Long id,
        String name,
        String level,
        Integer experienceYears,
        BigDecimal currentSalary
) {}
