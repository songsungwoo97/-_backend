package rank.example.rank.domain.user.exception;

import org.springframework.http.HttpStatus;
import rank.example.rank.domain.exceptionHandler.exception.UserException;

public class UserNotFoundException extends UserException {
    public UserNotFoundException(String message) {
        super("유저를 찾을 수 없음", HttpStatus.NOT_FOUND);
    }
}
