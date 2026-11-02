package com.mehmetsolak.social.presentation.api;

import com.mehmetsolak.social.application.shared.exception.ApplicationConflictException;
import com.mehmetsolak.social.domain.shared.exceptions.DomainConflictException;
import com.mehmetsolak.social.domain.shared.exceptions.DomainValidationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail handleValidation(MethodArgumentNotValidException exception) {
        Map<String, List<String>> validationErrors =
                exception.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .collect(Collectors.groupingBy(
                                FieldError::getField,
                                Collectors.mapping(
                                        DefaultMessageSourceResolvable::getDefaultMessage,
                                        Collectors.toList()
                                )
                        ));

        ProblemDetail problem = ProblemDetail.forStatus(
                HttpStatus.BAD_REQUEST
        );

        problem.setTitle("Validation Failed");
        problem.setDetail("One or more validation errors occurred.");
        problem.setProperty("validationErrors", validationErrors);

        return problem;
    }

    @ExceptionHandler(DomainValidationException.class)
    ProblemDetail handleDomainValidationExceptions(DomainValidationException exception) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.UNPROCESSABLE_CONTENT, exception.getMessage());
    }

    @ExceptionHandler(DomainConflictException.class)
    ProblemDetail handleDomainConflictExceptions(DomainConflictException exception) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(ApplicationConflictException.class)
    ProblemDetail handleApplicationConflictException(ApplicationConflictException exception) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT, exception.getMessage()
        );
    }
}
