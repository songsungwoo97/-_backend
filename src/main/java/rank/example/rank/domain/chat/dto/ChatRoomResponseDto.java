package rank.example.rank.domain.chat.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChatRoomResponseDto {

	@NotNull
	private Long chatroomId;

}
