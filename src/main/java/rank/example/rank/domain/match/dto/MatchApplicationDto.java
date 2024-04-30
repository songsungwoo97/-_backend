package rank.example.rank.domain.match.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rank.example.rank.domain.match.entity.MatchApplicationStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MatchApplicationDto {
    @NotNull
    private Long id;

    @NotNull
    private Long applicantId;

    @NotBlank
    private String applicantName;

    @NotNull
    private Long matchId;

    private MatchApplicationStatus status;
}
