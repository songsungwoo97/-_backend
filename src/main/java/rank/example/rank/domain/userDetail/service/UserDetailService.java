package rank.example.rank.domain.userDetail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rank.example.rank.domain.user.repository.UserRepository;
import rank.example.rank.domain.userDetail.dto.UserDetailDto;
import rank.example.rank.domain.userDetail.dto.UserDetailRankingDto;
import rank.example.rank.domain.userDetail.repository.UserDetailRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserDetailService {
    private final UserDetailRepository userDetailRepository;
    private final UserRepository userRepository;

    /**
     * 회원 디테일 조회 (id) getUserDetailByUserId
     *
     * 경기 후 회원 정보 업데이트
     *
     */
    public Optional<UserDetailDto> getUserDetailByUserId(Long userId) {
        return userDetailRepository.findUserDetailByUserId(userId);
    }
    public Page<UserDetailRankingDto> getRankedUserDetailsWithUserInfo(Long userId, Pageable pageable) {
        Page<UserDetailRankingDto> page = userDetailRepository.findAllUserDetailsWithUserInfo(pageable);
        List<UserDetailRankingDto> updatedContent = page.getContent().stream()
                .peek(dto -> dto.setIsCurrentUser(dto.getId().equals(userId)))
                .collect(Collectors.toList());

        return new PageImpl<>(updatedContent, pageable, page.getTotalElements());
    }
}
