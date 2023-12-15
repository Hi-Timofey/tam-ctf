package ru.katok.tamctf.service.errors;

public class GameError extends RuntimeException {

    public GameError() {
        super();
    }

    public GameError(final String message, final Throwable cause) {
        super(message, cause);
    }

    public GameError(final String message) {
        super(message);
    }

    public GameError(final Throwable cause) {
        super(cause);
    }
}

