package rank.example.rank.domain.exceptionHandler.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MatchException extends RuntimeException{
    private final HttpStatus status;

    public MatchException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
