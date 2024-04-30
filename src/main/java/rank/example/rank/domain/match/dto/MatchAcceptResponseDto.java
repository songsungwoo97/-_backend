package rank.example.rank.domain.match.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import rank.example.rank.domain.match.entity.Match;

import java.time.LocalDateTime;

@Data
public class MatchAcceptResponseDto {
    @NotNull
    private Long matchId;

    @NotBlank
    private String matchTitle;

    private LocalDateTime matchDateTime;

    @NotBlank
    private String hostName;

    @NotBlank
    private String opponentName;

    private String message;

    public static MatchAcceptResponseDto of(Match match, String message) {
        MatchAcceptResponseDto dto = new MatchAcceptResponseDto();
        dto.matchId = match.getId();
        dto.matchTitle = match.getTitle();
        dto.matchDateTime = match.getMatchDateTime();
        dto.hostName = match.getInitiator().getName();
        dto.message = message;
        return dto;
    }
}
