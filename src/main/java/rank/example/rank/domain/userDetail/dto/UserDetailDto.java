package rank.example.rank.domain.userDetail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rank.example.rank.domain.userDetail.entity.Tier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailDto {
    private Long id;
    private String name;
    private Double mmr;
    private double winRate;
//    private String gender;
    private Tier tier;
    private String introduction;
    private String address;
    private String profileImageUrl;
//    private Long ranking;
}
