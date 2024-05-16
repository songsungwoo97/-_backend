package rank.example.rank.domain.match.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import rank.example.rank.domain.jwt.TokenProvider;
import rank.example.rank.domain.match.dto.*;
import rank.example.rank.domain.match.entity.Match;
import rank.example.rank.domain.match.service.MatchService;

import java.util.List;

@RestController
@RequestMapping("/matches")
@RequiredArgsConstructor
@Slf4j
public class MatchController {
    private final MatchService matchService;
    private final TokenProvider tokenProvider;

    @PostMapping("/create")
    public MatchDto createMatch(@RequestBody MatchCreateRequestDto requestDto) {
        requestDto.setInitiatorId(tokenProvider.getMemberIdFromCurrentRequest());
        return matchService.createMatch(requestDto);
    }

    @PostMapping("/application/{matchId}")
    public List<MatchApplicationDto> applyForMatch(@PathVariable Long matchId, @RequestParam Long applicantId) throws Exception {
        applicantId =  tokenProvider.getMemberIdFromCurrentRequest();
        matchService.applyForMatch(matchId, applicantId);
        return matchService.getApplicationsForMatch(matchId);
    }

    @GetMapping("/list")
    public Page<MatchDto> getMatchList(
            @RequestBody MatchCondition matchCondition,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        log.info("로그111111 = {}", matchCondition.getTier());
        return matchService.findMatchesByCriteria(matchCondition, pageable);
    }

    @GetMapping("/detail/{matchId}")
    public MatchDto getMatchDetail(@PathVariable Long matchId) {
        return matchService.getMatchDetail(matchId);
    }

    @GetMapping("/user/{userId}/completed")
    public Page<Match> getCompletedMatchesByUserId(
            @PathVariable Long userId,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        userId = tokenProvider.getMemberIdFromCurrentRequest();
        return matchService.getCompletedMatchesByUserId(userId, pageable);
    }

    @GetMapping("/initiator/{userId}")
    public Page<Match> getMatchesByInitiator(
            @PathVariable Long userId,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        userId = tokenProvider.getMemberIdFromCurrentRequest();
        return matchService.getMatchesByInitiatorId(userId, pageable);
    }
}
