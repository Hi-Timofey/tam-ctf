package ru.katok.tamctf.api.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNoHandlerFound(NoHandlerFoundException e, WebRequest request) {
        //TODO: rewrite in more complex way + error handling
        HashMap<String, String> response = new HashMap<>();
        response.put("status", "fail");
        response.put("message", e.getLocalizedMessage());
        return response;
    }
}