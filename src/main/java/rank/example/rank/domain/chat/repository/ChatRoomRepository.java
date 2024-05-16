package rank.example.rank.domain.chat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rank.example.rank.domain.chat.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

	@Query("SELECT cr FROM ChatRoom cr WHERE cr.senderId = :id OR cr.inviteeId = :id")
	List<ChatRoom> findAllBySenderIdOrInviteeId(Long id);
}
