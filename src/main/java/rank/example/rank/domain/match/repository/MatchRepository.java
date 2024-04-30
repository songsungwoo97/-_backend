package rank.example.rank.domain.match.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import rank.example.rank.domain.match.entity.Match;

public interface MatchRepository extends JpaRepository<Match, Long>, QuerydslPredicateExecutor<Match> {
}
