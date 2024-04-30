package rank.example.rank.domain.userDetail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rank.example.rank.domain.userDetail.entity.UserDetail;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long>, CustomUserDetailRepository {
}
