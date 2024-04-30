package rank.example.rank.domain.match.dto;

import lombok.Data;
import rank.example.rank.domain.match.entity.Match;
import rank.example.rank.domain.match.entity.MatchSet;
import rank.example.rank.domain.user.entity.User;
import rank.example.rank.domain.userDetail.entity.Tier;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MatchDto {
    private Long id;
    private String title;
    private Long initiatorId;
    private String initiatorName;
    private Long opponentId;
    private String opponentName;
    private LocalDateTime matchDateTime;
    private String gender;
    private Tier tier;
    private String location;
    private String description;
    private String type;
    private String status;
    private List<MatchSet> matchSet;
    private User winner;
    private User loser;

    public static MatchDto fromEntity(Match match) {
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
        dto.setStatus(match.getStatus().name());
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
        dto.setStatus(match.getStatus().name());
        dto.setMatchSet(match.getMatchSets());
        dto.setWinner(match.getWinner());
        dto.setLoser(match.getLoser());
        return dto;
    }
}
