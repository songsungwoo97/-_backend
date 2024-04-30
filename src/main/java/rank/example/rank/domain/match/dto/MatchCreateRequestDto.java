package rank.example.rank.domain.match.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import rank.example.rank.domain.userDetail.entity.Tier;

@Data
public class MatchCreateRequestDto {
    @NotNull
    private Long initiatorId; // 방장 ID
//    private Long opponentId; //상대 ID

    private Tier tier;

    @NotNull
    private String gender;

    @NotBlank
    private String location;

    @NotBlank
    private String matchName;

    @NotBlank
    private String description; // 방소개(내용)
}
