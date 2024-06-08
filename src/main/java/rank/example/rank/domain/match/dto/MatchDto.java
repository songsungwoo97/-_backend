package rank.example.rank.domain.match.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rank.example.rank.domain.jwt.TokenProvider;
import rank.example.rank.domain.match.entity.Match;
import rank.example.rank.domain.match.entity.MatchSet;
import rank.example.rank.domain.match.entity.MatchStatus;
import rank.example.rank.domain.user.entity.User;
import rank.example.rank.domain.userDetail.entity.Tier;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class MatchDto {
    private Long id;
    private String title;
    private Long initiatorId;
    private String initiatorName;
    private String initiatorProfile;
    private Long opponentId;
    private String opponentName;
    private String opponentProfile;
    private LocalDateTime matchDateTime;
    private String gender;
    private Tier tier;
    private String location;
    private String description;
    private String type;
    private MatchStatus status;
    private List<MatchSet> matchSet;
    private User winner;
    private User loser;
    private Boolean draw;
    private Long userId;

    public static MatchDto fromEntity(Match match) {
        MatchDto dto = new MatchDto();
        dto.setId(match.getId());
        dto.setTitle(match.getTitle());
        dto.setInitiatorId(match.getInitiator().getId());
        dto.setInitiatorName(match.getInitiator().getName());
        if (match.getOpponent() != null) {
            User opponent = match.getOpponent();
            dto.setOpponentId(opponent.getId());
            dto.setOpponentName(opponent.getName());
            dto.setOpponentProfile(opponent.getProfileImageUrl());
        }
        dto.setMatchDateTime(match.getMatchDateTime());
        dto.setGender(match.getGender());
        dto.setTier(match.getTier());
        dto.setInitiatorProfile(match.getInitiator().getProfileImageUrl());
        dto.setLocation(match.getLocation());
        dto.setDescription(match.getDescription());
        dto.setStatus(match.getStatus());
        log.info("matchDto!!!!!!! = {}", dto);
        return dto;
    }

    public static MatchDto fromEntityFinish(Match match) {
        MatchDto dto = new MatchDto();
        dto.setId(match.getId());
        dto.setTitle(match.getTitle());
        dto.setInitiatorId(match.getInitiator().getId());
        dto.setInitiatorName(match.getInitiator().getName());
        User opponent = match.getOpponent();
        if (opponent != null) {
            dto.setOpponentId(opponent.getId());
            dto.setOpponentName(opponent.getName());
        }
        dto.setMatchDateTime(match.getMatchDateTime());
        dto.setGender(match.getGender());
        dto.setTier(match.getTier());
        dto.setLocation(match.getLocation());
        dto.setDescription(match.getDescription());
        dto.setStatus(match.getStatus());
        dto.setMatchSet(match.getMatchSets());
        dto.setWinner(match.getWinner());
        dto.setLoser(match.getLoser());
        dto.setDraw(match.getDraw());
        return dto;
    }
}
