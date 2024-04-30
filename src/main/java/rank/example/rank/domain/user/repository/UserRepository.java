package rank.example.rank.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rank.example.rank.domain.user.entity.OauthId;
import rank.example.rank.domain.user.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByOauthId(OauthId oauthId);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
}
