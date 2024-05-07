package rank.example.rank.domain.match.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import rank.example.rank.domain.match.dto.*;
import rank.example.rank.domain.match.service.MatchService;
import rank.example.rank.domain.user.entity.CustomUserDetail;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MatchWebSocketController {

    private final MatchService matchService;
    private final SimpMessagingTemplate template;

    @MessageMapping("/match/apply/{matchId}")
    @SendTo("/topic/match/{matchId}/applications")
    public List<MatchApplicationDto> applyMatch(@DestinationVariable Long matchId, ApplyRequestDto dto, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        log.info("매치idddddd = {}", matchId);
        Authentication authentication = (Authentication) headerAccessor.getSessionAttributes().get("authentication");
        CustomUserDetail principal2 = (CustomUserDetail) authentication.getPrincipal();
        Object principal = authentication.getPrincipal();
        log.info("토큰!!!!!!!!! = {}", principal);
        log.info("커스텀!!!!!!!!!! = {}", principal2.getUserId());
        log.info("커스텀!!!!!!!!!! = {}", principal2.getEmail());
        matchService.applyForMatch(matchId, principal2.getUserId());

        return matchService.getApplicationsForMatch(matchId);
    }

    @MessageMapping("/match/accept/{matchId}")
    @SendTo("/topic/match/{matchId}/accept")
    public MatchAcceptResponseDto acceptMatch(@DestinationVariable Long matchId, MatchAcceptRequestDto requestDto) throws Exception {
        MatchAcceptResponseDto responseDto = matchService.acceptMatch(requestDto);
        log.info("matchResponse = {}", responseDto);
        return responseDto;
    }

    @MessageMapping("/match/add/{matchId}")
    @SendTo("/topic/match/{matchId}/score")
    public List<MatchSetDto> addSetToMatch(@DestinationVariable Long matchId, AddSetToMatchRequestDto addSetToMatchRequestDto) {
        return matchService.addSetToMatch(matchId,
                addSetToMatchRequestDto.getSetNumber(),
                addSetToMatchRequestDto.getInitiatorScore(),
                addSetToMatchRequestDto.getOpponentScore());
    }

    @MessageMapping("/match/finish/{matchId}")
    @SendTo("/topic/match/{matchId}/finish")
    public MatchDto finishMatch(@DestinationVariable Long matchId, ApplyRequestDto dto) {
        return matchService.finalizeMatch(matchId);
    }
}
