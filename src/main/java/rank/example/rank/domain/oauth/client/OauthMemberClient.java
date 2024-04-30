package rank.example.rank.domain.oauth.client;

import rank.example.rank.domain.oauth.domain.OauthServerType;
import rank.example.rank.domain.user.entity.User;

public interface OauthMemberClient {
    OauthServerType supportServer();

    User fetchCustom(String code);
}
