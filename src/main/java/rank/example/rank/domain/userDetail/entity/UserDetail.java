package rank.example.rank.domain.userDetail.entity;

import jakarta.persistence.*;
import lombok.*;
import rank.example.rank.domain.user.entity.User;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double mmr;

    private Double winRate;

    private Long win;

    private Long draw;

    private Long lose;

    private String gender;

    private Tier tier;

    private String introduction;

    private Long ranking;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public void incrementWin() {
        if (this.win == null) {
            this.win = 0L;
        }
        this.win = this.win.longValue() + 1;
    }

    public void calculateWinRate() {
        if (this.win == null) {
            this.win = 0L;
        }
        if (this.draw == null) {
            this.draw = 0L;
        }
        if (this.lose == null) {
            this.lose = 0L;
        }

        long totalGames = this.win + this.draw + this.lose;

        if (totalGames > 0) {
            this.winRate = (double) this.win / totalGames * 100;
        } else {
            this.winRate = (double) 0;
        }
    }

    public void incrementLose() {
        if (this.lose == null) {
            this.lose = 0L;
        }
        this.lose += 1;
    }

    public void updateMmr(boolean isWinner) {
        if (this.mmr == null) {
            this.mmr = (double) 0;
        }

        if (isWinner) {
            this.mmr += 500;
        }

        else {
            this.mmr -= 500;
        }
    }
}
