package rank.example.rank.domain.match.dto;

import jakarta.validation.constraints.NotNull;


public class MatchApplicationCreateDto {
    @NotNull
    private Long applicantId;

    @NotNull
    private Long matchId;
}
