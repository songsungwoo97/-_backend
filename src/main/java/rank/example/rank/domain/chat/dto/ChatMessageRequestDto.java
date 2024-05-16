package rank.example.rank.domain.chat.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChatMessageRequestDto {

	@NotNull
	private Long senderId;
	@NotNull
	private String message;
}
