package ru.offer.candidate.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.stereotype.Component;
import ru.offer.candidate.dto.CandidateWriteDto;

import java.math.BigDecimal;
import java.util.Set;

@Component
public class ValidatorCandidate implements Validator {
    private static final String NAME_REGEX = "^[a-zA-Zа-яА-ЯёЁ\\s\\-'.]{2,100}$";
    private static final Set<String> ALLOWED_LEVELS = Set.of("JUNIOR", "MIDDLE", "SENIOR");

    @Override
    public boolean supports(Class<?> clazz) {
        return CandidateWriteDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CandidateWriteDto candidateWriteDto = (CandidateWriteDto) target;
        
        validateEmail(candidateWriteDto.getEmail(), errors);
        validatename(candidateWriteDto.getFirstName(), errors);
        validateLevel(candidateWriteDto.getLevel(), errors);
        validateBigDecimal(candidateWriteDto.getCurrentSalary(), "currentSalary", errors);

    }

    public void validateEmail(String email, Errors errors) {
        if (email == null || email.trim().isEmpty()) {
            errors.rejectValue("email", "email.is.empty", "Email must not be empty");
            return;
        }

        String trimmedEmail = email.trim().toLowerCase();

        if (trimmedEmail.length() > 254) {
            errors.rejectValue("email", "email.too.long", "Email is too long");
            return;
        }

        EmailValidator emailValidator = EmailValidator.getInstance(true, true);
        if (!emailValidator.isValid(trimmedEmail)) {
            errors.rejectValue("email", "email.invalid", "Invalid email address");
            return;
        }
    }

    private void validateLevel(String level, Errors errors) {
        if (level == null || level.trim().isEmpty()) {
            errors.rejectValue("level", "level.required", "Level must not be empty");
            return;
        }

        String upperLevel = level.trim().toUpperCase();
        if (!ALLOWED_LEVELS.contains(upperLevel)) {
            errors.rejectValue("level", "level.invalid",
                    "Level must be one of: " + ALLOWED_LEVELS);
        }
    }

    private void validatename(String name, Errors errors) {
        if (name == null || name.trim().isEmpty()) {
            errors.rejectValue("name", "name.required",
                    "Full name is required");
            return;
        }

        String trimmedName = name.trim();

        if (trimmedName.length() < 2) {
            errors.rejectValue("name", "name.too.short",
                    "Full name must be at least 2 characters");
        }

        if (trimmedName.length() > 50) {
            errors.rejectValue("name", "name.too.long",
                    "Full name must be less than 50 characters");
        }

        if (!trimmedName.matches(NAME_REGEX)) {
            errors.rejectValue("name", "name.invalid.chars",
                    "Name can only contain letters, spaces, hyphens and apostrophes");
        }
    }

    private void validateBigDecimal(BigDecimal value, String fieldName, Errors errors) {
        if (value == null) {
            errors.rejectValue(fieldName, fieldName + ".required",
                    fieldName + " is required");
            return;
        }

        if (value.compareTo(BigDecimal.ZERO) < 0) {
            errors.rejectValue(fieldName, fieldName + ".negative",
                    fieldName + " must not be negative");
        }
    }
}
