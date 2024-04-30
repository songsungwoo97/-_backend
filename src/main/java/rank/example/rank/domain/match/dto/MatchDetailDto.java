package rank.example.rank.domain.match.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import rank.example.rank.domain.userDetail.entity.Tier;

public class MatchDetailDto {
    @NotBlank
    private String name;

    @NotBlank
    private String matchName;

    @NotBlank
    private String profileImageUrl;

    @NotNull
    private String gender;

    @NotBlank
    private String description;

    private Tier tier;
}
