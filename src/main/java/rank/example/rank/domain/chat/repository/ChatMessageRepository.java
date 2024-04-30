package rank.example.rank.domain.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rank.example.rank.domain.chat.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

	List<ChatMessage> findByRoomId(Long roomId);
	void deleteByRoomId(Long roomId);
}
