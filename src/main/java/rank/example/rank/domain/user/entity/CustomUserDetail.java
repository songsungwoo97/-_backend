package rank.example.rank.domain.user.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUserDetail extends User {
    private final Long userId;
    private final String email;

    public CustomUserDetail(String username, String password,
                            Collection<? extends GrantedAuthority> authority, Long userId, String email) {
        super(username, password, authority);
        this.userId = userId;
        this.email = email;
    }
}
