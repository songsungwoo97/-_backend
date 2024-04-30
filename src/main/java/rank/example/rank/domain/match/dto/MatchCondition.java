package rank.example.rank.domain.match.dto;

import lombok.Data;
import rank.example.rank.domain.userDetail.entity.Tier;

@Data
public class MatchCondition {
    private Tier tier;
    private String gender;
    private String location;
}
