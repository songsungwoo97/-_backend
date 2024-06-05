package rank.example.rank.domain.match.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rank.example.rank.domain.user.entity.User;
import rank.example.rank.domain.userDetail.entity.Tier;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    private User initiator;

    @ManyToOne
    private User opponent;

    private LocalDateTime matchDateTime;

    private String gender;

    @Enumerated(EnumType.ORDINAL)
    private Tier tier;

    private String location;

    private String description;

    // 매치타입
    private String type;

    // 매치 상태
    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    @OneToMany(mappedBy = "match", fetch =  FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<MatchApplication> applications = new ArrayList<>();

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchSet> matchSets = new ArrayList<>();

    @ManyToOne
    private User winner;

    @ManyToOne
    private User loser;

    private Boolean draw = false;

    private int initiatorScore;

    private int opponentScore;
}
