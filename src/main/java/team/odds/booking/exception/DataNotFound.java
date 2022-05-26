package team.odds.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DataNotFound extends RuntimeException {

    // The serialVersionUID is a universal version identifier for a Serializable
    // class.
    // private static final long serialVersionUID = 1L;

    public DataNotFound(String message) {
        super(message);
    }

}