package rank.example.rank.domain.match.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import rank.example.rank.domain.exceptionHandler.exception.IllegalMatchStateException;
import rank.example.rank.domain.jwt.TokenProvider;
import rank.example.rank.domain.match.dto.*;
import rank.example.rank.domain.match.entity.*;
import rank.example.rank.domain.match.exception.MatchNotFoundException;
import rank.example.rank.domain.match.repository.MatchApplicationRepository;
import rank.example.rank.domain.match.repository.MatchRepository;
import rank.example.rank.domain.match.repository.MatchSetRepository;
import rank.example.rank.domain.user.entity.QUser;
import rank.example.rank.domain.user.entity.User;
import rank.example.rank.domain.user.exception.UserNotFoundException;
import rank.example.rank.domain.user.repository.UserRepository;
import rank.example.rank.domain.userDetail.entity.Tier;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MatchService {
    private final UserRepository userRepository;
    private final MatchRepository matchRepository;
    private final MatchApplicationRepository matchApplicationRepository;
    private final MatchSetRepository matchSetRepository;
    private final JPAQueryFactory queryFactory;
    private final TokenProvider tokenProvider;
    // TODO :
    // TODO :
    public MatchDto createMatch(MatchCreateRequestDto requestDto) {
        User initiator = userRepository.findById(requestDto.getInitiatorId())
                .orElseThrow(() -> new UserNotFoundException("Initiator not found id: " + requestDto.getInitiatorId()));
        Match match = Match.builder()
                .title(requestDto.getMatchName())
                .initiator(initiator)
                .matchDateTime(LocalDateTime.now())
                .gender(requestDto.getGender())
                .tier(requestDto.getTier())
                .location(requestDto.getLocation())
                .description(requestDto.getDescription())
//                .type(requestDto.getType())
                .status(MatchStatus.IN_PROGRESS)
                .build();

        match = matchRepository.save(match);
        return MatchDto.fromEntity(match);
    }

    @Transactional
    public void applyForMatch(Long matchId, Long applicantId) {
        User applicant = userRepository.findById(applicantId)
                .orElseThrow(() -> new UserNotFoundException("유저 못찾음 id: " + applicantId));

        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new MatchNotFoundException("매치 못찾음 id:" + matchId));

        if (match.getStatus() == MatchStatus.COMPLETED) {
            throw new IllegalMatchStateException("완료된 경기임 matchId : " + matchId);
        }

        boolean alreadyApplied = matchApplicationRepository.existsByMatchIdAndApplicantId(matchId, applicantId);
        if (alreadyApplied) {
            throw new IllegalMatchStateException("매치신청이 이미 받아들여짐 matchId: " + matchId);
        }

        MatchApplication application = new MatchApplication(applicant, match, MatchApplicationStatus.PENDING);
        matchApplicationRepository.save(application);
    }

    public List<MatchApplicationDto> getApplicationsForMatch(Long matchId) {
        return matchApplicationRepository.findMatchApplicationsByMatchId(matchId);
    }

    public MatchAcceptResponseDto acceptMatch(MatchAcceptRequestDto requestDto) {
        Match match = matchRepository.findById(requestDto.getMatchId())
                .orElseThrow(() -> new MatchNotFoundException("매치를 찾을 수 없음 id: " + requestDto.getMatchId()));
        if (match.getStatus() == MatchStatus.COMPLETED) {
            throw new IllegalMatchStateException("완료된 경기에서 match accept 불가 id: " + requestDto.getMatchId());
        }
        User opponent = userRepository.findById(requestDto.getOpponentId())
                .orElseThrow(() -> new UserNotFoundException("유저를 찾을 수 없음 id: " + requestDto.getOpponentId()));
        MatchApplication matchApplication = matchApplicationRepository.findById(requestDto.getMatchApplicationId())
                .orElseThrow(() -> new EntityNotFoundException("매치신청 없음"));

        match.setOpponent(opponent);
        matchApplication.setStatus(MatchApplicationStatus.APPROVED);
        match.setStatus(MatchStatus.IN_GAME);
        matchApplicationRepository.save(matchApplication);
        matchRepository.save(match);

        return MatchAcceptResponseDto.of(match, "매치가 성공적으로 수락됨.");
    }

    public MatchDto getMatchDetail(Long matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new MatchNotFoundException("매치 못찾음 id: " + matchId));
        return MatchDto.fromEntity(match);
    }

    @Transactional
    public List<MatchSetDto> addSetToMatch(Long matchId, int setNumber, int initiatorScore, int opponentScore) {
        Match match = matchRepository.findById(matchId).orElseThrow();
        if (match.getStatus() != MatchStatus.IN_PROGRESS) {
            throw new IllegalMatchStateException("매치가 진행중이 아닌 경우 세트 추가 불가 MatchId: " + matchId);
        }
        MatchSet set = new MatchSet(setNumber, initiatorScore, opponentScore, match);
        match.getMatchSets().add(set);
        matchSetRepository.save(set);
        matchRepository.save(match);
        return match.getMatchSets()
                .stream().map(MatchSetDto::fromEntity).collect(Collectors.toList());
    }

    @Transactional
    public MatchDto finalizeMatch(Long matchId) {
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new MatchNotFoundException("매치 못찾음 ID: " + matchId));
        if (match.getOpponent() == null) {
            throw new IllegalMatchStateException("Opponent 가 없음 id: " + matchId);
        }
        calculateMatchOutcome(match);
        match.setStatus(MatchStatus.COMPLETED);
        if (match.getWinner() != null) {
            log.info("winnerId = {}", match.getWinner().getId());
        } else {
            log.warn("No winner for match ID: {}", matchId);
        }
        matchRepository.save(match);
        return MatchDto.fromEntityFinish(match);
    }

    private void calculateMatchOutcome(Match match) {
        int initiatorWins = 0;
        int opponentWins = 0;
        int initiatorTotalScore = 0;
        int opponentTotalScore = 0;

        for (MatchSet set : match.getMatchSets()) {
            initiatorTotalScore += set.getInitiatorScore();
            opponentTotalScore += set.getOpponentScore();

            if (set.getInitiatorScore() > set.getOpponentScore()) {
                initiatorWins++;
            } else if (set.getInitiatorScore() < set.getOpponentScore()) {
                opponentWins++;
            }
        }

        match.setInitiatorScore(initiatorTotalScore);
        match.setOpponentScore(opponentTotalScore);

        if (initiatorWins > opponentWins) {
            match.setWinner(match.getInitiator());
            match.setLoser(match.getOpponent());
        } else if (opponentWins > initiatorWins) {
            match.setWinner(match.getOpponent());
            match.setLoser(match.getInitiator());
        } else {
            match.setDraw(true);
            match.setWinner(null);
            match.setLoser(null);
        }
        matchRepository.save(match);
    }

    public Page<MatchResponseDto> getMatchesByUserId(Long userId, Pageable pageable) {
        Page<MatchResponseDto> matches = matchRepository.findAllMatchesByUserId(userId, pageable);
        return Optional.ofNullable(matches)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new MatchNotFoundException("매치 찾을 수 없"));
    }

    public Page<MatchResponseDto> getCompletedMatchesByUserId(Long userId, Pageable pageable) {
        Page<MatchResponseDto> matches = matchRepository.findCompletedMatchesByUserId(userId, pageable);
        return Optional.ofNullable(matches)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new MatchNotFoundException("매치 찾을 수 없"));
    }

    public Page<MatchDto> findMatchesByCriteria(MatchCondition condition, Pageable pageable) {
        Page<Match> matches = matchRepository.findMatchesByCondition(condition, pageable);
        log.info("matchDtoCon!!!! = {}", matches);
        return matches.map(MatchDto::fromEntity);
    }
}
