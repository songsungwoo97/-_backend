package rank.example.rank.domain.match.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rank.example.rank.domain.match.entity.MatchStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchResponseDto {
    private String userName;
    private String userProfileImageUrl;
    private int userScore;
    private String opponentName;
    private String opponentProfileImageUrl;
    private int opponentScore;
    private Boolean isUserWinner;
    private Boolean isDraw;
    private MatchStatus status;
}
