package rank.example.rank.domain.userDetail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rank.example.rank.domain.userDetail.entity.Tier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailRankingDto {
    private Long id;
    private String name;
    private Double mmr;
    private Double winRate;
    private Long ranking;
    private Tier tier;
    private String profileImageUrl;
    private String address;
    private String gender;
    private Long age;
    private String introduction;
    private Long win;
    private Long draw;
    private Long lose;
    private Long totalGames;
    private Boolean isCurrentUser;

    public UserDetailRankingDto(Long id, String name, Double mmr, Double winRate, Long ranking, Tier tier,
                                String profileImageUrl, String address, String gender, Long age,
                                String introduction, Long win, Long draw, Long lose, Boolean isCurrentUser) {
        this.id = id;
        this.name = name;
        this.mmr = mmr;
        this.winRate = winRate;
        this.ranking = ranking;
        this.tier = tier;
        this.profileImageUrl = profileImageUrl;
        this.address = address;
        this.gender = gender;
        this.age = age;
        this.introduction = introduction;
        this.win = win;
        this.draw = draw;
        this.lose = lose;
        this.totalGames = win + draw + lose;
        this.isCurrentUser = isCurrentUser;
    }
}
