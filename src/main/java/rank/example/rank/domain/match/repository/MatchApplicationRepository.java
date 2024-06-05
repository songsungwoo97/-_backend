package rank.example.rank.domain.match.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rank.example.rank.domain.match.dto.MatchApplicationDto;
import rank.example.rank.domain.match.entity.MatchApplication;

import java.util.List;

public interface MatchApplicationRepository extends JpaRepository<MatchApplication, Long> {
    @Query("SELECT new rank.example.rank.domain.match.dto.MatchApplicationDto(" +
            "ma.id, " +
            "ma.applicant.id, " +
            "ma.applicant.name, " +
            "ma.match.id, " +
            "ma.status) " +
            "FROM MatchApplication ma " +
            "WHERE ma.match.id = :matchId")
    List<MatchApplicationDto> findMatchApplicationsByMatchId(@Param("matchId") Long matchId);

    boolean existsByMatchIdAndApplicantId(Long matchId, Long applicantId);
}
