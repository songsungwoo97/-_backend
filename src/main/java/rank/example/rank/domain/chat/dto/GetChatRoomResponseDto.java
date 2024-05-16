package rank.example.rank.domain.chat.dto;

import lombok.Data;

@Data
public class GetChatRoomResponseDto {
	private Long roomId;

	private Long senderId;

	private Long inviteeId;
}
