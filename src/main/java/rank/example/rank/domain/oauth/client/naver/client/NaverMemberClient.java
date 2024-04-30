package rank.example.rank.domain.oauth.client.naver.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import rank.example.rank.domain.oauth.client.OauthMemberClient;
import rank.example.rank.domain.oauth.client.naver.dto.NaverMemberResponse;
import rank.example.rank.domain.oauth.client.naver.dto.NaverOauthConfig;
import rank.example.rank.domain.oauth.client.naver.dto.NaverToken;
import rank.example.rank.domain.oauth.domain.OauthServerType;
import rank.example.rank.domain.user.entity.User;
import rank.example.rank.domain.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class NaverMemberClient implements OauthMemberClient {
    private final NaverApiClient naverApiClient;
    private final NaverOauthConfig naverOauthConfig;
    private final UserRepository userRepository;

    @Override
    public OauthServerType supportServer() {
        return OauthServerType.NAVER;
    }

    @Override
    public User fetchCustom(String authCode) {
        NaverToken tokenInfo = naverApiClient.fetchToken(tokenRequestParams(authCode));
        NaverMemberResponse naverMemberResponse = naverApiClient.fetchMember("Bearer " + tokenInfo.accessToken());
        return naverMemberResponse.toUserEntity();
    }

    private MultiValueMap<String, String> tokenRequestParams(String authCode) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", naverOauthConfig.clientId());
        params.add("client_secret", naverOauthConfig.clientSecret());
        params.add("code", authCode);
        params.add("state", naverOauthConfig.state());
        return params;
    }
}
