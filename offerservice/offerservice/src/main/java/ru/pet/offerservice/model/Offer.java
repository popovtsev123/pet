package ru.pet.offerservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Document(collection = "offers")
public class Offer {
    @Id
    private String id;
    private Long candidateId;
    private BigDecimal recommendedSalary;
    private Double experienceCoefficient;
    private Double levelCoefficient;
    private Double marketCoefficient;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer version;
}
