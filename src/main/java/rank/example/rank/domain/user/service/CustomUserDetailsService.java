package rank.example.rank.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import rank.example.rank.domain.user.entity.CustomUserDetail;
import rank.example.rank.domain.user.entity.User;
import rank.example.rank.domain.user.exception.UserNotFoundException;
import rank.example.rank.domain.user.repository.UserRepository;

import java.util.Collections;
import java.util.List;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    //username -> email
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(this::createUser)
                .orElseThrow(() -> new UserNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    //username -> email
    private UserDetails createUser(User user) {
        List<GrantedAuthority> grantedAuthorityList = Collections.singletonList(new SimpleGrantedAuthority(user.getUserType().name()));
        return new CustomUserDetail(
                user.getEmail(),
                user.getPassword(),
                grantedAuthorityList,
                user.getId(),
                user.getNickname()
        );
    }
}
