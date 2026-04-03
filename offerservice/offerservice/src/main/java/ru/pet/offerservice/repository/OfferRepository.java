package ru.pet.offerservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.pet.offerservice.model.Offer;

import java.util.Optional;

public interface OfferRepository extends MongoRepository<Offer, String> {
    Optional<Offer> findByCandidateId(Long candidateId);
}
