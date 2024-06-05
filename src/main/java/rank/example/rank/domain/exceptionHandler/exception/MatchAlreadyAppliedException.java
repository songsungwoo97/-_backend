package rank.example.rank.domain.exceptionHandler.exception;

import org.springframework.http.HttpStatus;

public class MatchAlreadyAppliedException extends MatchException {
    public MatchAlreadyAppliedException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
