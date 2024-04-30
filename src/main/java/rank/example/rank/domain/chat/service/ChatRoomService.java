package rank.example.rank.domain.chat.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import rank.example.rank.domain.chat.entity.ChatRoom;
import rank.example.rank.domain.chat.repository.ChatRoomRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomService {

	final private ChatRoomRepository chatRoomRepository;
	final private ChatMessageService chatMessageService;

	@Transactional
	public ChatRoom createChatRoom(Long senderId, Long inviteeId) {
		ChatRoom chatRoom = new ChatRoom(senderId, inviteeId);
		return chatRoomRepository.save(chatRoom);
	}

	public ChatRoom findById(Long roomId) {
		return chatRoomRepository.findById(roomId)
			.orElseThrow(() -> new NoSuchElementException("채팅방을 찾을 수 없습니다."));
	}

	@Transactional
	public boolean deleteChatRoom(Long roomId) {
		if (chatRoomRepository.existsById(roomId)) {
			chatRoomRepository.deleteById(roomId);
			chatMessageService.deleteChatDataInRedis(roomId);
			chatMessageService.deleteChatDataByInMySQL(roomId);
			return true;
		} else {
			return false;
		}
	}
}
