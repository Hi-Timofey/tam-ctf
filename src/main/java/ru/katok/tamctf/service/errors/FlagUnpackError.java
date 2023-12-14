package ru.katok.tamctf.service.errors;

public class FlagUnpackError extends RuntimeException {
    public FlagUnpackError() {
        super();
    }

    public FlagUnpackError(final String message, final Throwable cause) {
        super(message, cause);
    }

    public FlagUnpackError(final String message) {
        super(message);
    }
}
