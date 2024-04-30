package rank.example.rank.domain.oauth.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rank.example.rank.domain.oauth.AuthCodeRequestUrlProviderComposite;
import rank.example.rank.domain.oauth.client.OauthMemberClientComposite;
import rank.example.rank.domain.oauth.domain.OauthServerType;
import rank.example.rank.domain.user.entity.User;
import rank.example.rank.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class OauthService {
    private final AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;
    private final OauthMemberClientComposite oauthMemberClientComposite;
    private final UserRepository userRepository;

    public String getAuthCodeRequestUrl(OauthServerType oauthServerType) {
        return authCodeRequestUrlProviderComposite.provide(oauthServerType);
    }

    public User login(OauthServerType oauthServerType, String authCode) {
        User user = oauthMemberClientComposite.fetchCustom(oauthServerType, authCode);
        return userRepository.findByOauthId(user.getOauthId())
                .orElseGet(() -> {
                    user.createUserDetail(); // UserDetail 초기화
                    return userRepository.save(user);
                });}
}
