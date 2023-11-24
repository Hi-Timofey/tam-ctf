package ru.katok.tamctf.domain.error;

public class TaskExistsException extends RuntimeException {
    public TaskExistsException() {
        super();
    }

    public TaskExistsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TaskExistsException(final String message) {
        super(message);
    }

    public TaskExistsException(final Throwable cause) {
        super(cause);
    }
}
