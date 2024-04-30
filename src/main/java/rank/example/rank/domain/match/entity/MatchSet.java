package rank.example.rank.domain.match.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchSet {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private int setNumber;
    private int initiatorScore;
    private int opponentScore;

    @ManyToOne
    private Match match;

    public MatchSet(int setNumber, int initiatorScore, int opponentScore, Match match) {
        this.setNumber = setNumber;
        this.initiatorScore = initiatorScore;
        this.opponentScore = opponentScore;
        this.match = match;
    }
}
