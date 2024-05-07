package rank.example.rank.domain.userDetail.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rank.example.rank.domain.userDetail.dto.UserDetailRankingDto;
import rank.example.rank.domain.userDetail.entity.UserDetail;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long>, CustomUserDetailRepository {
    @Query("SELECT new rank.example.rank.domain.userDetail.dto.UserDetailRankingDto(" +
            "ud.id, u.name, ud.mmr, ud.winRate, ud.ranking, ud.tier, " +
            "u.profileImageUrl, u.address, u.gender, u.age, " +
            "ud.introduction, ud.win, ud.draw, ud.lose, false) " +
            "FROM UserDetail ud " +
            "JOIN ud.user u " +
            "ORDER BY ud.ranking ASC")
    Page<UserDetailRankingDto> findAllUserDetailsWithUserInfo(Pageable pageable);
}
