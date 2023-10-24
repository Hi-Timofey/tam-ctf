package ru.katok.tamctf.api.util;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@Getter
@Setter
@AllArgsConstructor
public class GenericResponse {
    private boolean succsessful;
    private String message;
    private Throwable error;

    public GenericResponse(final Exception error) {
        super();
        this.succsessful = false;
        this.message = error.getMessage();
        this.error = error;
    }

    public GenericResponse(final boolean succsessful, final String message) {
        super();
        this.message = message;
        this.succsessful = succsessful;
    }


//    public GenericResponse(List<ObjectError> allErrors, String error) {
//        this.error = error;
//        this.succsessful = false;
//        String temp = allErrors.stream().map(e -> {
//            if (e instanceof FieldError) {
//                return "{\"field\":\"" + ((FieldError) e).getField() + "\",\"defaultMessage\":\"" + e.getDefaultMessage() + "\"}";
//            } else {
//                return "{\"object\":\"" + e.getObjectName() + "\",\"defaultMessage\":\"" + e.getDefaultMessage() + "\"}";
//            }
//        }).collect(Collectors.joining(","));
//        this.message = "[" + temp + "]";
//    }
}