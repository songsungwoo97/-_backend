package rank.example.rank.domain.match.exception;

import org.springframework.http.HttpStatus;
import rank.example.rank.domain.exceptionHandler.exception.MatchException;

public class MatchNotFoundException extends MatchException {
    public MatchNotFoundException(String message) {
        super("매치를 찾을 수 없음", HttpStatus.NOT_FOUND);
    }
}
