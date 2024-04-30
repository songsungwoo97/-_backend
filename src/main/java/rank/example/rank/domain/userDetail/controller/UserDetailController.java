package rank.example.rank.domain.userDetail.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rank.example.rank.domain.userDetail.dto.UserDetailDto;
import rank.example.rank.domain.userDetail.entity.UserDetail;
import rank.example.rank.domain.userDetail.service.UserDetailService;

import java.util.Optional;

@RestController
@RequestMapping("/userDetail")
@RequiredArgsConstructor
public class UserDetailController {
    private final UserDetailService userDetailService;

    /**
     * 회원 디테일 조회 getUserDetail
     *
     *
     */

    @GetMapping("/{userId}")
    public Optional<UserDetailDto> getUserDetail(@PathVariable Long userId) {
        return userDetailService.getUserDetailByUserId(userId);
    }
}
