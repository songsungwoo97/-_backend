package rank.example.rank.domain.match.repository;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import rank.example.rank.domain.match.dto.MatchCondition;
import rank.example.rank.domain.match.dto.MatchResponseDto;
import rank.example.rank.domain.match.entity.Match;
import rank.example.rank.domain.match.entity.QMatch;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long>, QuerydslPredicateExecutor<Match> {
    @Query("select m from Match m where m.initiator.id = :userId and m.status = rank.example.rank.domain.match.entity.MatchStatus.COMPLETED")
    Page<Match> findAllCompletedMatchesByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("select m from Match m where m.initiator.id = :userId and m.status <> rank.example.rank.domain.match.entity.MatchStatus.COMPLETED")
    Page<Match> findAllMatchingMatchesByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("select m from Match m where m.initiator.id = :userId")
    Page<Match> findAllMatchesByInitiatorId(@Param("userId") Long userId, Pageable pageable);

    @Query("select m from Match m where m.opponent.id = :userId")
    Page<Match> findAllMatchesByOpponentId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT new rank.example.rank.domain.match.dto.MatchResponseDto(" +
            "CASE WHEN m.initiator.id = :userId THEN m.initiator.name ELSE m.opponent.name END, " +
            "CASE WHEN m.initiator.id = :userId THEN m.initiator.profileImageUrl ELSE m.opponent.profileImageUrl END, " +
            "CASE WHEN m.initiator.id = :userId THEN m.initiatorScore ELSE m.opponentScore END, " +
            "CASE WHEN m.initiator.id = :userId THEN m.opponent.name ELSE m.initiator.name END, " +
            "CASE WHEN m.initiator.id = :userId THEN m.opponent.profileImageUrl ELSE m.initiator.profileImageUrl END, " +
            "CASE WHEN m.initiator.id = :userId THEN m.opponentScore ELSE m.initiatorScore END, " +
            "CASE WHEN m.winner.id = :userId THEN TRUE ELSE FALSE END, " +
            "m.draw, " +  // Add the isDraw field
            "m.status) " +
            "FROM Match m " +
            "WHERE m.initiator.id = :userId OR m.opponent.id = :userId")
    Page<MatchResponseDto> findAllMatchesByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT new rank.example.rank.domain.match.dto.MatchResponseDto(" +
            "CASE WHEN m.initiator.id = :userId THEN m.initiator.name ELSE m.opponent.name END, " +
            "CASE WHEN m.initiator.id = :userId THEN m.initiator.profileImageUrl ELSE m.opponent.profileImageUrl END, " +
            "CASE WHEN m.initiator.id = :userId THEN m.initiatorScore ELSE m.opponentScore END, " +
            "CASE WHEN m.initiator.id = :userId THEN m.opponent.name ELSE m.initiator.name END, " +
            "CASE WHEN m.initiator.id = :userId THEN m.opponent.profileImageUrl ELSE m.initiator.profileImageUrl END, " +
            "CASE WHEN m.initiator.id = :userId THEN m.opponentScore ELSE m.initiatorScore END, " +
            "CASE WHEN m.winner.id = :userId THEN TRUE ELSE FALSE END, " +
            "m.draw, " +  // Add the isDraw field
            "m.status) " +
            "FROM Match m " +
            "WHERE (m.initiator.id = :userId OR m.opponent.id = :userId) AND m.status = 'COMPLETED'")
    Page<MatchResponseDto> findCompletedMatchesByUserId(@Param("userId") Long userId, Pageable pageable);

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
