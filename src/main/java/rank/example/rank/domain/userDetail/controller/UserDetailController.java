package rank.example.rank.domain.userDetail.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rank.example.rank.domain.jwt.TokenProvider;
import rank.example.rank.domain.userDetail.dto.UserDetailDto;
import rank.example.rank.domain.userDetail.dto.UserDetailRankingDto;
import rank.example.rank.domain.userDetail.service.UserDetailService;

import java.util.Optional;

@RestController
@RequestMapping("/userDetail")
@RequiredArgsConstructor
public class UserDetailController {
    private final UserDetailService userDetailService;
    private final TokenProvider tokenProvider;

    /**
     * 회원 디테일 조회 getUserDetail
     */

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailDto> getUserDetail(@PathVariable Long userId) {
        userId = tokenProvider.getMemberIdFromCurrentRequest();
        Optional<UserDetailDto> detailDtoOptional = userDetailService.getUserDetailByUserId(userId);
        return detailDtoOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/rankings/{userId}")
    public Page<UserDetailRankingDto> getRankedUserDetailsWithUserInfo(
            @PathVariable Long userId,
            @PageableDefault(size = 10, page = 0) // 0으로 변경
            Pageable pageable) {
        userId = tokenProvider.getMemberIdFromCurrentRequest();
        return userDetailService.getRankedUserDetailsWithUserInfo(userId, pageable);
    }
}
