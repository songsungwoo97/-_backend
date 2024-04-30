package rank.example.rank.domain.oauth.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rank.example.rank.domain.jwt.TokenProvider;
import rank.example.rank.domain.oauth.domain.OauthServerType;
import rank.example.rank.domain.oauth.service.OauthService;
import rank.example.rank.domain.user.entity.User;

@RequiredArgsConstructor
@RequestMapping("/oauth")
@RestController
@Slf4j
public class OauthController {
    private final OauthService oauthService;
    private final TokenProvider tokenProvider;

    @SneakyThrows
    @GetMapping("/{oauthServerType}")
    ResponseEntity<Void> redirectAuthCodeRequestUrl(
            @PathVariable OauthServerType oauthServerType,
            HttpServletResponse response)
    {
        String redirectUrl = oauthService.getAuthCodeRequestUrl(oauthServerType);
        response.sendRedirect(redirectUrl);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login/{oauthServerType}")
    ResponseEntity<String> login(
            @PathVariable OauthServerType oauthServerType,
            @RequestParam String code)
    {
        User user = oauthService.login(oauthServerType, code);
        String token = tokenProvider.
                createTokenOauth(user);
        return ResponseEntity.ok(token);
    }
}
