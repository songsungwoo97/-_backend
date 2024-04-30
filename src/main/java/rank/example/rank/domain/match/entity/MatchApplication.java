package rank.example.rank.domain.match.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rank.example.rank.domain.user.entity.User;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id")
    private User applicant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private Match match;

    @Enumerated(EnumType.STRING)
    private MatchApplicationStatus status;


    public MatchApplication(User applicant, Match match, MatchApplicationStatus status) {
        this.applicant = applicant;
        this.match = match;
        this.status = status;
    }
}
