package rank.example.rank.domain.chat.dto;

import lombok.Data;

@Data
public class ChatMessageRequestDto {

	private Long senderId;
	private String message;
}
