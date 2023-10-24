package ru.katok.tamctf.domain.error;

public class TeamAlreadyExistException extends RuntimeException{
    public TeamAlreadyExistException() {
        super();
    }

    public TeamAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TeamAlreadyExistException(final String message) {
        super(message);
    }

    public TeamAlreadyExistException(final Throwable cause) {
        super(cause);
    }
}


