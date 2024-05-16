package rank.example.rank.domain.chat.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateChatRoomDto {
	@NotNull
	private Long senderId;
	@NotNull
	private Long inviteeId;

	private String name;
}
