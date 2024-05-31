package rank.example.rank.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateUserInfoDto {
    private Long userId;
    private String gender;
    private String address;
    private String phone;
    private Long age;
}
