package ru.katok.tamctf.api.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GenericResponse {
    private boolean successful;
    private String message;
    private Throwable error;

    public GenericResponse() {
        super();
        this.message = "WTF";
        this.successful = false;
    }

    public GenericResponse(final Exception error) {
        super();
        this.successful = false;
        this.message = error.getMessage();
        this.error = error;
    }

    public GenericResponse(final boolean successful, final String message) {
        super();
        this.message = message;
        this.successful = successful;
    }
}