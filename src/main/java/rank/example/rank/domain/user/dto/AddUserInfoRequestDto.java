package rank.example.rank.domain.user.dto;

import lombok.Data;

@Data
public class AddUserInfoRequestDto {
    private Long id;
//    private String email;
    private String gender;
    private String address;
    private String phone;
    private Long age;
}
