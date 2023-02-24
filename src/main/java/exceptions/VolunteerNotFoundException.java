package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VolunteerNotFoundException extends RuntimeException {

    public VolunteerNotFoundException() {
        super("Volunteer is not found!");
    }
}
