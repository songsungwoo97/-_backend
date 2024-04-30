package rank.example.rank.domain.match.dto;

import lombok.Data;
import rank.example.rank.domain.match.entity.MatchSet;

@Data
public class MatchSetDto {
    private Long id;
    private int setNumber;
    private int initiatorScore;
    private int opponentScore;

    // 생성자, getter 및 setter 생략

    public static MatchSetDto fromEntity(MatchSet matchSet) {
        MatchSetDto dto = new MatchSetDto();
        dto.setId(matchSet.getId());
        dto.setSetNumber(matchSet.getSetNumber());
        dto.setInitiatorScore(matchSet.getInitiatorScore());
        dto.setOpponentScore(matchSet.getOpponentScore());
        return dto;
    }
}
