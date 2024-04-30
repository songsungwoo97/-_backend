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
        KakaoToken tokenInfo = kakaoApiClient.fetchToken(tokenRequestParams(authCode));
        KakaoMemberResponse kakaoMemberResponse = kakaoApiClient.
                fetchMember("Bearer " + tokenInfo.accessToken());
        return kakaoMemberResponse.toUserEntity();
    }

    private MultiValueMap<String, String> tokenRequestParams(String authCode) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoOauthConfig.clientId());
        params.add("redirect_uri", kakaoOauthConfig.redirectUri());
        params.add("code", authCode);
        params.add("client_secret", kakaoOauthConfig.clientSecret());
        return params;
    }
}
