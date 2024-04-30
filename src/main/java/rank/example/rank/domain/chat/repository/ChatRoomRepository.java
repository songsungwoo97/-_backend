package rank.example.rank.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rank.example.rank.domain.chat.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

}
