package rank.example.rank.domain.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import rank.example.rank.domain.jwt.TokenProvider;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthChannelInterceptor implements ChannelInterceptor {

    private final TokenProvider tokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();

        // 연결 요청일 때만 인증 정보를 처리
        if (StompCommand.CONNECT.equals(command)) {
            String token = accessor.getFirstNativeHeader("Authorization");
            if (token != null && tokenProvider.validateToken(token)) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                accessor.getSessionAttributes().put("authentication", authentication);
                log.info("Authentication successful. User: {}", authentication.getName());
            } else {
                log.info("No valid authorization token found.");
            }
        }

        return message;
    }
}