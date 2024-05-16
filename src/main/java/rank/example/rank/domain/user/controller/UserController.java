package rank.example.rank.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rank.example.rank.domain.jwt.TokenProvider;
import rank.example.rank.domain.user.dto.AddUserInfoRequestDto;
import rank.example.rank.domain.user.entity.User;
import rank.example.rank.domain.user.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping("/add-info")
    public User updateUserEntity(@RequestBody AddUserInfoRequestDto addUserInfoRequestDto) {
        addUserInfoRequestDto.setId(tokenProvider.getMemberIdFromCurrentRequest());
        return userService.addUserInfo(addUserInfoRequestDto);
    }
}
