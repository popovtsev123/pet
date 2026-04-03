package ru.pet.offerservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.pet.offerservice.model.Offer;
import ru.pet.offerservice.service.OfferService;
import java.util.List;

@RestController
@RequestMapping("/api/offers")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @GetMapping("/{candidateId}")
    public Offer getOffer(@PathVariable Long candidateId) {
        return offerService.getOfferByCandidateId(candidateId);
    }

    @GetMapping
    public List<Offer> getAllOffers() {
        return offerService.getAllOffers();
    }
}
