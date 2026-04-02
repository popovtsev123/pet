package ru.offer.candidate.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
public class ValidationException extends RuntimeException {

    private final BindingResult bindingResult;

    public ValidationException(String message) {
        super(message);
        this.bindingResult = null;
    }

    public ValidationException(BindingResult bindingResult) {
        super("Validation failed: " + bindingResult.getFieldErrorCount() + " error(s)");
        this.bindingResult = bindingResult;
    }

}