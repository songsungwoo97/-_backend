package rank.example.rank.domain.chat.service;

import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import rank.example.rank.domain.chat.entity.ChatMessage;
import rank.example.rank.domain.chat.repository.ChatMessageRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatMessageService {

	private final ChatMessageRepository chatMessageRepository;
	private final RedisTemplate<String, ChatMessage> chatRedisTemplate;

	// 채팅 메시지를 MySQL과 Redis에 저장하는 메서드
	@Transactional
	public void saveChatMessage(ChatMessage chatMessage) {
		chatMessageRepository.save(chatMessage);
		saveChatMessageToRedis(chatMessage);
	}

	// 채팅 메시지를 Redis에 저장하는 메서드
	@Transactional
	public void saveChatMessageToRedis(ChatMessage chatMessage) {
		String key = "room:" + chatMessage.getRoomId();
		chatRedisTemplate.opsForList().rightPush(key, chatMessage);
	}

	// 특정 채팅방의 채팅 메시지를 Redis에서 가져오는 메서드
	public List<ChatMessage> getChatMessagesFromRedis(Long roomId) {
		String key = "room:" + roomId;
		return chatRedisTemplate.opsForList().range(key, 0, -1);
	}

	// 특정 채팅방의 채팅 메시지를 MySQL에서 가져오는 메서드
	public List<ChatMessage> getChatMessagesFromMySQL(Long roomId) {
		return chatMessageRepository.findByRoomId(roomId);
	}

	// 특정 채팅방의 채팅 메시지를 Redis 또는 MySQL에서 가져오는 메서드
	public List<ChatMessage> getChatMessages(Long roomId) {
		List<ChatMessage> chatMessages = getChatMessagesFromRedis(roomId);
		if (chatMessages.isEmpty()) {
			chatMessages = getChatMessagesFromMySQL(roomId);
			if (!chatMessages.isEmpty()) {
				saveChatMessagesToRedis(roomId, chatMessages);
			}
		}
		return chatMessages;
	}

	// 채팅 메시지 목록을 Redis에 저장하는 메서드
	private void saveChatMessagesToRedis(Long roomId, List<ChatMessage> chatMessages) {
		String key = "chat:" + roomId;
		chatRedisTemplate.opsForList().rightPushAll(key, chatMessages);
	}

	@Transactional
	public void deleteChatDataByInMySQL(Long roomId) {
		chatMessageRepository.deleteByRoomId(roomId);
	}

	// Redis에서 특정 키의 데이터를 삭제하는 메서드
	public void deleteChatDataInRedis(Long roomId) {
		String redisKey = "room:" + roomId;
		chatRedisTemplate.delete(redisKey);
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