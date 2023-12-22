package ru.katok.tamctf.service.errors;

public class TelegramBotException extends RuntimeException{
    public TelegramBotException() {super();}

    public TelegramBotException(final String message, final Throwable cause) {super(message, cause);}

    public TelegramBotException(final String message) {super(message);}

    public TelegramBotException(final Throwable cause) {
        super(cause);
    }
}

