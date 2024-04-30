package rank.example.rank.domain.match.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rank.example.rank.domain.match.entity.MatchSet;

public interface MatchSetRepository extends JpaRepository<MatchSet, Long> {
}
