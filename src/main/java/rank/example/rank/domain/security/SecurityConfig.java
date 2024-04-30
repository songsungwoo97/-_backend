package rank.example.rank.domain.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import rank.example.rank.domain.jwt.JwtAccessDeniedHandler;
import rank.example.rank.domain.jwt.JwtAuthenticationEntryPoint;
import rank.example.rank.domain.jwt.JwtFilter;
import rank.example.rank.domain.jwt.TokenProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CorsConfig corsConfig;
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity

                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
                // CSRF 설정 비활성화
                .csrf(AbstractHttpConfigurer::disable)

                // 예외 처리
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler))

                .headers(headers ->
                        headers
                                .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))

                // 세션 관리를 STATELESS로 설정
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 요청에 대한 접근 제한 설정
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/api/user/**").permitAll()
//                        .requestMatchers(
//                                "/api/auth/**").permitAll()
//                        .requestMatchers(
//                                "/oauth/**").permitAll()
////                        .anyRequest().authenticated())
//                        .anyRequest().authenticated())
                        .anyRequest().permitAll())

                // JwtFilter 적용
                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
