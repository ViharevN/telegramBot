package bot.sky.telegrambot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PersonNotFoundException extends RuntimeException {
    public static final long serialVersionUID = 1L;
    public PersonNotFoundException(String message) {
        super(message);
    }
}
