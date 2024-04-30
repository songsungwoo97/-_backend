package rank.example.rank.domain.chat.controller;

import java.util.NoSuchElementException;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import rank.example.rank.domain.chat.dto.ChatMessageRequestDto;
import rank.example.rank.domain.chat.entity.ChatMessage;
import rank.example.rank.domain.chat.service.ChatMessageService;
import rank.example.rank.domain.user.entity.User;
import rank.example.rank.domain.user.repository.UserRepository;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

	final private SimpMessagingTemplate messagingTemplate;
	final private UserRepository userRepository;
	final private ChatMessageService chatMessageService;

	/**
	 * 첫인사
	 * @param roomId
	 * @param chat
	 */
	@MessageMapping(value = "/initchat/{roomId}")
	public void joinChatRoom(@DestinationVariable("roomId") Long roomId, ChatMessageRequestDto chat){

		User member = userRepository.findById(chat.getSenderId()).orElseThrow(() -> new NoSuchElementException("멤버가 없습니다."));

		//roomId를 통해 찾은 방에
		chat.setMessage(member.getName() + "님이 채팅방에 참여하였습니다.");
		messagingTemplate.convertAndSend("/chat/" + roomId, chat);
	}

	/**
	 * 채팅 메시지 전송
	 * @param roomId
	 * @param chat
	 */
	@MessageMapping(value = "/chat/{roomId}")
	public void sendMessage(@DestinationVariable("roomId") Long roomId, ChatMessageRequestDto chat) {

		ChatMessage message = new ChatMessage();
		message.setRoomId(roomId);
		message.setSenderId(chat.getSenderId());
		message.setMessage(chat.getMessage());
		chatMessageService.saveChatMessage(message);
		chatMessageService.saveChatMessageToRedis(message);
		messagingTemplate.convertAndSend("/chat/" + roomId, chat);
	}
}
