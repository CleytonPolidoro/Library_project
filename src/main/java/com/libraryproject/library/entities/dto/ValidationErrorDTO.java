package com.libraryproject.library.entities.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationErrorDTO extends StandardErrorDTO {

    private List<FieldMessageDTO> erros = new ArrayList<>();

    public ValidationErrorDTO(Instant timestamp, Integer status, String error, String message, String path) {
        super(timestamp, status, error, message, path);
    }

    public List<FieldMessageDTO> getErros() {
        return erros;
    }

    public void addError(String fieldName, String message) {
        erros.removeIf(x -> x.getFieldName().equals(fieldName));

        erros.add(new FieldMessageDTO(fieldName, message));
    }
}
