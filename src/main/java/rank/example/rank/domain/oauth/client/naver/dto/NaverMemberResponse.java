package rank.example.rank.domain.oauth.client.naver.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.extern.slf4j.Slf4j;
import rank.example.rank.domain.oauth.domain.OauthServerType;
import rank.example.rank.domain.user.entity.OauthId;
import rank.example.rank.domain.user.entity.User;
import rank.example.rank.domain.user.entity.UserType;

@Slf4j
public record NaverMemberResponse(
        String resultcode,
        String message,
        Response response
) {
    public User toUserEntity() {
        return User.builder()
                .oauthId(new OauthId(String.valueOf(response.id), OauthServerType.NAVER))
                .nickname(response.nickname)
                .profileImageUrl(response.profileImage)
                .email(response.email)
                .name(response.name)
                .userType(UserType.ROLE_GUEST)
                .build();
    }

    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Response(
            String id,
            String nickname,
            String name,
            String email,
            String gender,
            String age,
            String birthday,
            String profileImage,
            String birthyear,
            String mobile
    ) {
    }
}
