package rank.example.rank.domain.chat.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class ChatRoom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Long senderId;

	@NotNull
	private Long inviteeId;

	public ChatRoom(Long senderId, Long inviteeId) {
		this.senderId = senderId;
		this.inviteeId = inviteeId;
	}
}