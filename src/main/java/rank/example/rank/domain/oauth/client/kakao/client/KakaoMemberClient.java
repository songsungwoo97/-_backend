package rank.example.rank.domain.oauth.client.kakao.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import rank.example.rank.domain.oauth.client.OauthMemberClient;
import rank.example.rank.domain.oauth.client.kakao.dto.KakaoOauthConfig;
import rank.example.rank.domain.oauth.client.kakao.dto.KakaoMemberResponse;
import rank.example.rank.domain.oauth.client.kakao.dto.KakaoToken;
import rank.example.rank.domain.oauth.domain.OauthServerType;
import rank.example.rank.domain.user.entity.User;

@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoMemberClient implements OauthMemberClient {
    private final KakaoApiClient kakaoApiClient;
    private final KakaoOauthConfig kakaoOauthConfig;

    @Override
    public OauthServerType supportServer() {
        return OauthServerType.KAKAO;
    }

    @Override
    public User fetchCustom(String authCode) {
        log.info("Fetching token for auth code: {}", authCode);
        KakaoToken tokenInfo = kakaoApiClient.fetchToken(tokenRequestParams(authCode));
        log.info("Received token info: {}", tokenInfo);

        log.info("Fetching member information with access token: {}", tokenInfo.accessToken());
        KakaoMemberResponse kakaoMemberResponse = kakaoApiClient.fetchMember("Bearer " + tokenInfo.accessToken());
        log.info("Received member response: {}", kakaoMemberResponse);

        return kakaoMemberResponse.toUserEntity();
    }

    private MultiValueMap<String, String> tokenRequestParams(String authCode) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoOauthConfig.clientId());
        params.add("redirect_uri", kakaoOauthConfig.redirectUri());
        params.add("code", authCode);
        params.add("client_secret", kakaoOauthConfig.clientSecret());

        log.info("Requesting token with parameters: {}", params);
        return params;
    }
}
