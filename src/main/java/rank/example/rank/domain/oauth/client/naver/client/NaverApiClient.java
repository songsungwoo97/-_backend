package rank.example.rank.domain.oauth.client.naver.client;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import rank.example.rank.domain.oauth.client.naver.dto.NaverMemberResponse;
import rank.example.rank.domain.oauth.client.naver.dto.NaverToken;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public interface NaverApiClient {
    @PostExchange(url = "https://nid.naver.com/oauth2.0/token")
    NaverToken fetchToken(@RequestParam MultiValueMap<String, String> params);

    @GetExchange("https://openapi.naver.com/v1/nid/me")
    NaverMemberResponse fetchMember(@RequestHeader(name = AUTHORIZATION) String bearerToken);
}
