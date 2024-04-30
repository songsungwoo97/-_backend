package rank.example.rank.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rank.example.rank.domain.user.dto.AddUserInfoRequestDto;
import rank.example.rank.domain.user.dto.UserDto;
import rank.example.rank.domain.user.entity.User;
import rank.example.rank.domain.user.exception.UserNotFoundException;
import rank.example.rank.domain.user.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User addUserInfo(AddUserInfoRequestDto addUserInfoRequestDto) {
        return userRepository.findById(addUserInfoRequestDto.getId())
                .map(user -> {
                    User updatedUser = User.builder()
                            .id(user.getId()) // 기존 유저의 ID를 유지합니다.
                            .email(addUserInfoRequestDto.getEmail())
                            .gender(addUserInfoRequestDto.getGender())
                            .address(addUserInfoRequestDto.getAddress())
                            .phoneNumber(addUserInfoRequestDto.getPhone())
                            .age(addUserInfoRequestDto.getAge())
                            .build();
                    return userRepository.save(updatedUser);
                }).orElseThrow(() -> new UserNotFoundException("User not found with id: " + addUserInfoRequestDto.getId()));
    }
}
