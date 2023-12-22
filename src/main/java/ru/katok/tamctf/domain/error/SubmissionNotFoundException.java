package ru.katok.tamctf.domain.error;

public class SubmissionNotFoundException extends RuntimeException{
    public SubmissionNotFoundException() {
        super();
    }

    public SubmissionNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public SubmissionNotFoundException(final String message) {
        super(message);
    }

    public SubmissionNotFoundException(final Throwable cause) {
        super(cause);
    }
}
