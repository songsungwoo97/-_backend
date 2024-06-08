package rank.example.rank.domain.chat.service;

import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import rank.example.rank.domain.chat.entity.ChatMessage;
import rank.example.rank.domain.chat.repository.ChatMessageRepository;
import rank.example.rank.domain.jwt.TokenProvider;
import rank.example.rank.domain.user.entity.User;
import rank.example.rank.domain.user.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatMessageService {

	private final ChatMessageRepository chatMessageRepository;
	private final UserRepository userRepository;
	private final TokenProvider tokenProvider;

	@Transactional
	public void saveChatMessage(ChatMessage chatMessage) {
		chatMessageRepository.save(chatMessage);
	}

	public List<ChatMessage> getChatMessagesFromMySQL(Long roomId) {
		return chatMessageRepository.findByRoomId(roomId);
	}

	public List<ChatMessage> getChatMessages(Long roomId) {
		List<ChatMessage> chatMessages =  getChatMessagesFromMySQL(roomId);
		User user = userRepository.findById(tokenProvider.getMemberIdFromCurrentRequest()).orElseThrow();
		for (ChatMessage chatMessage : chatMessages) {
			chatMessage.setUserId(user.getId());
		}
		return chatMessages;
	}

	@Transactional
	public void deleteChatDataByInMySQL(Long roomId) {
		chatMessageRepository.deleteByRoomId(roomId);
	}

	@Transactional
	public void sendJoinMessage(Long roomId, Long senderId) {
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setRoomId(roomId);
		chatMessage.setSenderId(senderId);
		chatMessage.setMessage(senderId + "님이 채팅방에 참여하였습니다.");
		saveChatMessage(chatMessage);
	}
}