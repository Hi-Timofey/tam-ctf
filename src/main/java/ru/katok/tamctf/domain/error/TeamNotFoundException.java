package ru.katok.tamctf.domain.error;

public class TeamNotFoundException extends RuntimeException {

    public TeamNotFoundException() {
        super();
    }

    public TeamNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TeamNotFoundException(final String message) {
        super(message);
    }

    public TeamNotFoundException(final Throwable cause) {
        super(cause);
    }
}
