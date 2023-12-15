package ru.katok.tamctf.domain.error;

public class UserAlreadyInTeamException extends RuntimeException {

    public UserAlreadyInTeamException() {
        super();
    }

    public UserAlreadyInTeamException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyInTeamException(final String message) {
        super(message);
    }

    public UserAlreadyInTeamException(final Throwable cause) {
        super(cause);
    }

}
