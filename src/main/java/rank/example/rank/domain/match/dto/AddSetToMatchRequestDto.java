package rank.example.rank.domain.match.dto;

import lombok.Data;

@Data
public class AddSetToMatchRequestDto {
    private int setNumber;
    private int initiatorScore;
    private int opponentScore;
}
