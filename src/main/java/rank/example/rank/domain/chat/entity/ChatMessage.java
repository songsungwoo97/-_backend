package rank.example.rank.domain.chat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

//@Entity
@Document(collection = "chat_message")
@Getter @Setter
public class ChatMessage {

	@Id
	private String id;
	private Long senderId; //보낸 사람
	private String message; //메시지 내용
	private Long roomId;
	private Long userId;

	public ChatMessage() {}
	public ChatMessage(Long senderId, String message) {
		this.senderId = senderId;
		this.message = message;
	}
}
