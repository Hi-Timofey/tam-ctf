package ru.katok.tamctf.domain.error;

public class HintNotFoundException extends RuntimeException {
    public HintNotFoundException() {
        super();
    }

    public HintNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public HintNotFoundException(final String message) {
        super(message);
    }

    public HintNotFoundException(final Throwable cause) {
        super(cause);
    }
}

