package ru.offer.candidate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import ru.offer.candidate.dto.CandidateCreatedEvent;
import ru.offer.candidate.dto.CandidateWriteResponseDto;
import ru.offer.candidate.dto.IndividualDto;
import ru.offer.candidate.dto.IndividualPageDto;
import ru.offer.candidate.entity.CandidateEntity;
import ru.offer.candidate.entity.Level;
import ru.offer.candidate.kafka.KafkaEventPublisher;
import ru.offer.candidate.repository.CandidateRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CandidateService {
    private final CandidateRepository candidateRepository;

    private final KafkaEventPublisher eventPublisher;

    @Transactional
    public CandidateWriteResponseDto addCandidate(String firstName, String lastName, String email, Level level,
                                                  Integer experience, BigDecimal salary) {
        CandidateEntity candidateEntity = CandidateEntity.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .level(level)
                .experience(experience)
                .currentSalary(salary).build();

        CandidateEntity candidate = candidateRepository.save(candidateEntity);

        sendEvent(candidate);

        CandidateWriteResponseDto candidateWriteResponseDto = new CandidateWriteResponseDto();
        candidateWriteResponseDto.id(candidate.getId());

        return candidateWriteResponseDto;
    }

    public IndividualPageDto getCandidatesByLevel(List<String> levels) {
        List<CandidateEntity> candidateEntitiesByLevel = candidateRepository.getCandidateEntitiesByLevel(levels);
        IndividualPageDto individualPageDto = new IndividualPageDto();

        candidateEntitiesByLevel.stream().map(this::convertToIndividualDto).
                forEach(individualPageDto::addItemsItem);

        return individualPageDto;
    }

    public IndividualPageDto getCandidatesByExperience(List<String> experiences) {
        List<CandidateEntity> candidateEntitiesByLevel =
                candidateRepository.getCandidateEntitiesByExperiences(experiences);
        IndividualPageDto individualPageDto = new IndividualPageDto();

        candidateEntitiesByLevel.stream().map(this::convertToIndividualDto).
                forEach(individualPageDto::addItemsItem);

        return individualPageDto;
    }

    public IndividualDto getCandidateById(Long id) {
        Optional<CandidateEntity> candidateEntity = candidateRepository.findById(id);

        return candidateEntity.map(this::convertToIndividualDto).orElse(null);
    }

    private IndividualDto convertToIndividualDto(CandidateEntity candidateEntity) {
        IndividualDto individualDto = new IndividualDto();
        individualDto.setFirstName(candidateEntity.getFirstName());
        individualDto.setLastName(candidateEntity.getLastName());
        individualDto.setEmail(candidateEntity.getEmail());
        individualDto.setCurrentSalary(candidateEntity.getCurrentSalary());
        individualDto.setExperience(candidateEntity.getExperience());
        individualDto.setLevel(candidateEntity.getLevel().getLevelName());

        return individualDto;
    }

    private void sendEvent(CandidateEntity candidate) {
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        CandidateCreatedEvent event = new CandidateCreatedEvent(
                                UUID.randomUUID(),
                                candidate.getId(),
                                candidate.getFirstName(),
                                candidate.getLevel().getLevelName(),
                                candidate.getExperience(),
                                candidate.getCurrentSalary()
                        );
                        eventPublisher.publish(event);
                    }
                }
        );
    }
}
