package rank.example.rank.domain.exceptionHandler.exception;

import org.springframework.http.HttpStatus;

public class IllegalMatchStateException extends MatchException {
    public IllegalMatchStateException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
