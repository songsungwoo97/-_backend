package rank.example.rank.domain.match.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rank.example.rank.domain.match.dto.MatchCondition;
import rank.example.rank.domain.match.entity.Match;

public interface CustomMatchRepository {
    Page<Match> findMatchesByCondition(MatchCondition condition, Pageable pageable);
}
