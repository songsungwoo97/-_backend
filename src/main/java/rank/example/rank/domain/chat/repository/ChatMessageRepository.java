package rank.example.rank.domain.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import rank.example.rank.domain.chat.entity.ChatMessage;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

	List<ChatMessage> findByRoomId(Long roomId);
	void deleteByRoomId(Long roomId);
}
