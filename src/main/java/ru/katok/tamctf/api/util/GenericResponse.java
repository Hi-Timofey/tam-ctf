package ru.katok.tamctf.api.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GenericResponse<T> {
    private boolean succsessful;
    private String message;
    private Throwable error;
    private T data;

    public GenericResponse() {
        super();
        this.message = "WTF";
        this.succsessful = false;
    }

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

    public GenericResponse(final boolean succsessful, final String message, final T responseObject) {
        super();
        this.message = message;
        this.succsessful = succsessful;
        this.data = responseObject;
    }
}