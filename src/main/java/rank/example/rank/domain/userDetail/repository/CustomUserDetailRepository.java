package rank.example.rank.domain.userDetail.repository;

import rank.example.rank.domain.userDetail.dto.UserDetailDto;
import rank.example.rank.domain.userDetail.entity.UserDetail;

import java.util.Optional;

public interface CustomUserDetailRepository {
    Optional<UserDetail> findMemberWithMostGames();
    Optional<UserDetailDto> findUserDetailByUserId(Long userId);
}
