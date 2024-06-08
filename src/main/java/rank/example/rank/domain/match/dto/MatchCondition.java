package rank.example.rank.domain.match.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rank.example.rank.domain.userDetail.entity.Tier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchCondition {
    private Tier tier;
    private String gender;
    private String location;
}
