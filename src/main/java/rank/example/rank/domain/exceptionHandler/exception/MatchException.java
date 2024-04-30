package rank.example.rank.domain.exceptionHandler.exception;

import org.springframework.http.HttpStatus;

public class MatchException extends RuntimeException{
    private HttpStatus status;

    public MatchException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
