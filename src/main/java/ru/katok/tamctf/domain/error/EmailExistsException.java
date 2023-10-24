package ru.katok.tamctf.domain.error;

@SuppressWarnings("serial")
public class EmailExistsException extends RuntimeException {

    public EmailExistsException(final String message) {
        super(message);
    }

}