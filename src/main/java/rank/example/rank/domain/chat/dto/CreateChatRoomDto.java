package rank.example.rank.domain.chat.dto;

import lombok.Data;

@Data
public class CreateChatRoomDto {

	private Long senderId;

	private Long inviteeId;
}
