package rank.example.rank.domain.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import rank.example.rank.domain.user.entity.CustomUserDetail;
import rank.example.rank.domain.user.entity.User;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Component
@Slf4j
public class TokenProvider implements InitializingBean {
    private static final String AUTHORITIES_KEY = "auth";
    private static final String USER_ID_KEY = "userId";
    private static final String USER_TYPE_KEY = "userType";
    private static final String EMAIL_KEY = "email";
    private final String secret;
    private final long tokenValidityInMilliseconds;
    private Key key;

    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createTokenOauth(User user) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(user.getName())
                .claim(AUTHORITIES_KEY, user.getUserType().name())
                .claim(USER_ID_KEY, user.getId())
                .claim(EMAIL_KEY, user.getEmail())
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .toList();

//        org.springframework.security.core.userdetails.User principal = new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities);
        CustomUserDetail principal = new CustomUserDetail(
                claims.getSubject(), // username
                "", // password (not used)
                authorities,
                claims.get(USER_ID_KEY, Long.class), // userId
                claims.get(EMAIL_KEY, String.class) // email
        );

        log.info("토크은!!!!!!!! = {}", principal.getUserId());

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public Long getMemberIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Long memberId = claims.get("userId", Long.class);
        log.info("Extracted 선림 from token: {}", memberId);
        return claims.get("userId", Long.class);
    }

    public Long getMemberIdFromCurrentRequest() {
        HttpServletRequest request = getCurrentRequest();
        log.info("설리번2 = {}",request);
        if (request != null) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                log.info("설리번3 = {}", token);
                return getMemberIdFromToken(token);
            }
        }
        return null;
    }

    private HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs != null ? attrs.getRequest() : null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {

            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {

            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {

            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {

            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
