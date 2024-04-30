package rank.example.rank.domain.user.exception;

import org.springframework.http.HttpStatus;
import rank.example.rank.domain.exceptionHandler.exception.UserException;

public class InvalidUserIdException extends UserException {
    public InvalidUserIdException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
