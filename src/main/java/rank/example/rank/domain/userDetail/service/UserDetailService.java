package rank.example.rank.domain.userDetail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rank.example.rank.domain.user.exception.UserNotFoundException;
import rank.example.rank.domain.user.repository.UserRepository;
import rank.example.rank.domain.userDetail.dto.UserDetailDto;
import rank.example.rank.domain.userDetail.entity.UserDetail;
import rank.example.rank.domain.userDetail.repository.UserDetailRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailService {
    private final UserDetailRepository userDetailRepository;
    private final UserRepository userRepository;

    /**
     * 회원 디테일 조회 (id) getUserDetailByUserId
     *
     * 경기 후 회원 정보 업데이트
     *
     */

    @Transactional(readOnly = true)
    public Optional<UserDetailDto> getUserDetailByUserId(Long userId) {
        return Optional.ofNullable(userDetailRepository.findUserDetailByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("유저를 찾을 수 없습니다.")));
    }
}
