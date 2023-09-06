package com.cabral.disney.payload.response;

import com.cabral.disney.exception.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse {
    private List<ValidationError> errors = new ArrayList<>();

    public void addError(String field, String message) {
        ValidationError error = new ValidationError(field, message);
        errors.add(error);
    }

    public List<ValidationError> getErrors() {
        return errors;
    }
}
