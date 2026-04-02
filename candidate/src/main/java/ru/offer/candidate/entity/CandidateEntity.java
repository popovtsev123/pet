package ru.offer.candidate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Table(name = "candidate")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    @Column(length = 50, nullable = false, name = "first_name")
    private String firstName;

    @Size(max = 50)
    @Column(length = 50, name = "last_name")
    private String lastName;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Level level;

    private Integer experience;

    private BigDecimal currentSalary;
}
