package rank.example.rank.domain.match.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import rank.example.rank.domain.match.dto.*;
import rank.example.rank.domain.match.service.MatchService;

import java.util.List;

@RestController
@RequestMapping("/matches")
@RequiredArgsConstructor
@Slf4j
public class MatchController {
    private final MatchService matchService;

    @PostMapping("/create")
    public MatchDto createMatch(@RequestBody MatchCreateRequestDto requestDto) {
        return matchService.createMatch(requestDto);
    }

    @PostMapping("/application/{matchId}")
    public List<MatchApplicationDto> applyForMatch(@PathVariable Long matchId, @RequestParam Long applicantId) throws  Exception {
        matchService.applyForMatch(matchId, applicantId);
        return matchService.getApplicationsForMatch(matchId);
    }

    @GetMapping("/list")
    public Page<MatchDto> getMatchList(
            @RequestBody MatchCondition matchCondition,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return matchService.findMatchesByCriteria(matchCondition, pageRequest);
    }

    @GetMapping("/detail/{matchId}")
    public MatchDto getMatchDetail(@PathVariable Long matchId) {
        return matchService.getMatchDetail(matchId);
    }
}
