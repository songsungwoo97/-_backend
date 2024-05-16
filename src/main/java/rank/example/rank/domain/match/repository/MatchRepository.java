package rank.example.rank.domain.match.repository;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import rank.example.rank.domain.match.dto.MatchCondition;
import rank.example.rank.domain.match.entity.Match;
import rank.example.rank.domain.match.entity.QMatch;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long>, QuerydslPredicateExecutor<Match> {
    @Query("select m from Match m where m.initiator.id = :userId and m.status = rank.example.rank.domain.match.entity.MatchStatus.COMPLETED")
    Page<Match> findAllCompletedMatchesByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("select m from Match m where m.initiator.id = :userId")
    Page<Match> findAllMatchesByInitiatorId(@Param("userId") Long userId, Pageable pageable);

    default Page<Match> findMatchesByCondition(MatchCondition condition, Pageable pageable) {
        QMatch qMatch = QMatch.match;
        BooleanBuilder builder = new BooleanBuilder();

        if (condition.getTier() != null) {
            builder.or(qMatch.tier.eq(condition.getTier()));
        }
        if (condition.getGender() != null) {
            builder.or(qMatch.gender.eq(condition.getGender()));
        }
        if (condition.getLocation() != null) {
            builder.or(qMatch.location.eq(condition.getLocation()));
        }

        return findAll(builder, pageable);
    }
}
