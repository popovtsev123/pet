package ru.pet.offerservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.pet.offerservice.dto.CalculationCompletedEvent;
import ru.pet.offerservice.model.Offer;
import ru.pet.offerservice.repository.OfferRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;

    public void processCalculation(CalculationCompletedEvent event) {
        Offer existing = offerRepository.findByCandidateId(event.getCandidateId()).orElse(null);
        if (existing != null) {
            existing.setRecommendedSalary(event.getRecommendedSalary());
            existing.setExperienceCoefficient(event.getExperienceCoefficient());
            existing.setLevelCoefficient(event.getLevelCoefficient());
            existing.setMarketCoefficient(event.getMarketCoefficient());
            existing.setUpdatedAt(LocalDateTime.now());
            existing.setVersion(existing.getVersion() == null ? 1 : existing.getVersion() + 1);
            offerRepository.save(existing);
            log.info("Updated offer for candidate {}", event.getCandidateId());
        } else {
            Offer offer = new Offer();
            offer.setCandidateId(event.getCandidateId());
            offer.setRecommendedSalary(event.getRecommendedSalary());
            offer.setExperienceCoefficient(event.getExperienceCoefficient());
            offer.setLevelCoefficient(event.getLevelCoefficient());
            offer.setMarketCoefficient(event.getMarketCoefficient());
            offer.setCreatedAt(LocalDateTime.now());
            offer.setUpdatedAt(LocalDateTime.now());
            offer.setVersion(1);
            offerRepository.save(offer);
            log.info("Saved new offer for candidate {}", event.getCandidateId());
        }
    }

    public Offer getOfferByCandidateId(Long candidateId) {
        return offerRepository.findByCandidateId(candidateId)
                .orElseThrow(() -> new RuntimeException("Offer not found for candidate: " + candidateId));
    }

    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }
}
