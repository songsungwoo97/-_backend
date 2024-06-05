package rank.example.rank.domain.match.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import rank.example.rank.domain.jwt.TokenProvider;
import rank.example.rank.domain.match.dto.*;
import rank.example.rank.domain.match.service.MatchService;
import rank.example.rank.domain.userDetail.entity.Tier;

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
            @RequestParam Tier tier,
            @RequestParam String gender,
            @RequestParam String location,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        MatchCondition matchCondition = new MatchCondition();
        matchCondition.setTier(tier);
        matchCondition.setGender(gender);
        matchCondition.setLocation(location);
        log.info("로그111111 = {}", matchCondition.getTier());
        return matchService.findMatchesByCriteria(matchCondition, pageable);
    }

    @GetMapping("/detail/{matchId}")
    public MatchDto getMatchDetail(@PathVariable Long matchId) {
        return matchService.getMatchDetail(matchId);
    }

    @GetMapping("/user/{userId}")
    public Page<MatchResponseDto> getMatchesByUser(
            @PathVariable Long userId,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return matchService.getMatchesByUserId(userId, pageable);
    }

    @GetMapping("/user/{userId}/completed")
    public Page<MatchResponseDto> getMatchesCompleted(
            @PathVariable Long userId,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return matchService.getCompletedMatchesByUserId(userId, pageable);
    }
}
