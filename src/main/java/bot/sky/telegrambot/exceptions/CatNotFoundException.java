package bot.sky.telegrambot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CatNotFoundException extends RuntimeException {
    public static final long serialVersionUID = 1L;

    public CatNotFoundException(String message) {
        super(message);
    }
}
