package rank.example.rank.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rank.example.rank.domain.userDetail.entity.Tier;
import rank.example.rank.domain.userDetail.entity.UserDetail;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users",
    uniqueConstraints = {
        @UniqueConstraint(
                name = "oauth_id_unique",
                columnNames = {"oauth_server_id", "oauth_server"}
        )
    }
)
@AttributeOverrides({
        @AttributeOverride(name="oauthId.oauthServerId", column=@Column(name="oauth_server_id")),
        @AttributeOverride(name="oauthId.oauthServerType", column=@Column(name="oauth_server"))
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private UserDetail userDetail;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    private String email;

    private String password;

    private String name;

    private String profileImageUrl;

    private String nickname;

    private String address;

    private String gender;

    private String phoneNumber;

    private Long age;

    @Embedded
    private OauthId oauthId;

    public void createUserDetail() {
        if (this.userDetail == null) {
            this.userDetail = userDetail.builder()
                    .win(0L)
                    .draw(0L)
                    .lose(0L)
                    .mmr(1000.0)
                    .winRate(0.0)
                    .gender("unknown")
                    .tier(Tier.BRONZE)
                    .introduction("")
                    .build();
            this.userDetail.setUser(this);
        }
    }
}
