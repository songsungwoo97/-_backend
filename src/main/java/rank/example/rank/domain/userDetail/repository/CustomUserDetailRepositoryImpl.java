package rank.example.rank.domain.userDetail.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import rank.example.rank.domain.user.entity.QUser;
import rank.example.rank.domain.userDetail.dto.UserDetailDto;
import rank.example.rank.domain.userDetail.entity.QUserDetail;
import rank.example.rank.domain.userDetail.entity.UserDetail;

import java.util.Optional;

import static rank.example.rank.domain.user.entity.QUser.user;
import static rank.example.rank.domain.userDetail.entity.QUserDetail.userDetail;

@RequiredArgsConstructor
public class CustomUserDetailRepositoryImpl implements CustomUserDetailRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<UserDetail> findMemberWithMostGames() {
        return Optional.empty();
    }

    @Override
    public Optional<UserDetailDto> findUserDetailByUserId(Long userId) {
        UserDetailDto detailDto = queryFactory
                .select(Projections.constructor(UserDetailDto.class,
                        userDetail.id,
                        user.name,
                        userDetail.mmr,
                        userDetail.winRate,
                        userDetail.win,
                        userDetail.draw,
                        userDetail.lose,
                        userDetail.gender,
                        userDetail.tier,
                        userDetail.introduction,
                        user.address,
                        user.profileImageUrl,
                        userDetail.ranking))
                .from(userDetail)
                .join(userDetail.user, user)
                .where(userDetail.user.id.eq(userId))
                .fetchOne();

        return Optional.ofNullable(detailDto);
    }
}

