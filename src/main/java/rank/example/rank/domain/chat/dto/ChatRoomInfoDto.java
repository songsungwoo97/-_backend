package rank.example.rank.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomInfoDto {
    private Long opponentId;
    private String opponentName;
    private String imageUrl;
}
