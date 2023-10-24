package ru.katok.tamctf.domain.error;

public class TeamTypeError extends RuntimeException{

    public TeamTypeError() {
        super();
    }

    public TeamTypeError(final String message) {
        super(message);
    }
}
