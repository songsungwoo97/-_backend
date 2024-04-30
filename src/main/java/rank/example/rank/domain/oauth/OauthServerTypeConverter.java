package rank.example.rank.domain.oauth;

import org.springframework.core.convert.converter.Converter;
import rank.example.rank.domain.oauth.domain.OauthServerType;

public class OauthServerTypeConverter implements Converter<String, OauthServerType> {
    @Override
    public OauthServerType convert(String source) {
        return OauthServerType.fromName(source);
    }
}
