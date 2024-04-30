package rank.example.rank.domain.oauth;

import rank.example.rank.domain.oauth.domain.OauthServerType;

public interface AuthCodeRequestUrlProvider {
    OauthServerType supportServer();

    String provide();
}
