package rank.example.rank.domain.match.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MatchAcceptRequestDto {
    @NotNull
    private Long matchId;

    @NotNull
    private Long opponentId;

    private Long matchApplicationId;
}
