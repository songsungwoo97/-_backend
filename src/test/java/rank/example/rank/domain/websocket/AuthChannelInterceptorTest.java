//package rank.example.rank.domain.websocket;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import rank.example.rank.domain.jwt.TokenProvider;
//import rank.example.rank.domain.websocket.AuthChannelInterceptor;
//
//import static org.mockito.Mockito.*;
//
//public class AuthChannelInterceptorTest {
//
//    @Mock
//    private TokenProvider tokenProvider;
//
//    @Mock
//    private MessageChannel channel;
//
//    @InjectMocks
//    private AuthChannelInterceptor interceptor;
//
//    public AuthChannelInterceptorTest() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testPreSend_ValidToken() {
//        // 토큰 설정
//        String token = "valid.token";
//        Authentication authentication = mock(Authentication.class);
//
//        // StompHeaderAccessor 준비
//        StompHeaderAccessor accessor = StompHeaderAccessor.create(org.springframework.messaging.simp.stomp.StompCommand.SEND);
//        accessor.addNativeHeader("Authorization", token);
//        // Message 준비
//        Message<byte[]> message = MessageBuilder.withPayload(new byte[0]).copyHeaders(accessor.toMessageHeaders()).build();
//
//        // 토큰 유효성 검증 및 인증 처리 모의
//        when(tokenProvider.validateToken(token)).thenReturn(true);
//        when(tokenProvider.getAuthentication(token)).thenReturn(authentication);
//
//        // preSend 메소드 호출
//        interceptor.preSend(message, channel);
//
//        // 검증
//        verify(tokenProvider).validateToken(token);
//        verify(tokenProvider).getAuthentication(token);
//        assert SecurityContextHolder.getContext().getAuthentication() == authentication;
//    }
//}
