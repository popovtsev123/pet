package ru.offer.candidate.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;
import ru.offer.candidate.api.CandidateApi;
import ru.offer.candidate.dto.CandidateWriteDto;
import ru.offer.candidate.dto.CandidateWriteResponseDto;
import ru.offer.candidate.dto.IndividualDto;
import ru.offer.candidate.dto.IndividualPageDto;
import ru.offer.candidate.entity.Level;
import ru.offer.candidate.exception.ValidationException;
import ru.offer.candidate.service.CandidateService;
import ru.offer.candidate.validator.ValidatorCandidate;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CandidateRestController implements CandidateApi {

    private final CandidateService candidateService;

    private final ValidatorCandidate validator;

    @Override
    public ResponseEntity<IndividualPageDto> findAllByExperience(List<String> experience) {
        return ResponseEntity.ok(candidateService.getCandidatesByExperience(experience));
    }

    @Override
    public ResponseEntity<IndividualPageDto> findAllByLevel(List<String> levels) {
        return ResponseEntity.ok(candidateService.getCandidatesByLevel(levels));
    }

    @Override
    public ResponseEntity<IndividualDto> findById(Long id) {
        return ResponseEntity.ok(candidateService.getCandidateById(id));
    }


    @Override
    public ResponseEntity<CandidateWriteResponseDto> registration(CandidateWriteDto dto) {
        BindingResult bindingResult = new BeanPropertyBindingResult(dto, "candidateWriteDto");
        validator.validate(dto, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        CandidateWriteResponseDto candidateWriteResponseDto = candidateService.addCandidate(dto.getFirstName(),
                dto.getLastName(), dto.getEmail(), Level.valueOf(dto.getLevel()), dto.getExperience(),
                dto.getCurrentSalary());

        return ResponseEntity.status(HttpStatus.CREATED).body(candidateWriteResponseDto);
    }
}
