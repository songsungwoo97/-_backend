package rank.example.rank.domain.chat.controller;

import java.util.NoSuchElementException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import rank.example.rank.domain.chat.dto.ChatMessageRequestDto;
import rank.example.rank.domain.chat.entity.ChatMessage;
import rank.example.rank.domain.chat.service.ChatMessageService;
import rank.example.rank.domain.user.entity.User;
import rank.example.rank.domain.user.repository.UserRepository;

@RestController
@RequiredArgsConstructor
@Slf4j
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
	@SendTo("/send/hgh/{roomId}")
	public ChatMessageRequestDto joinChatRoom(@DestinationVariable("roomId") Long roomId, ChatMessageRequestDto chat){

		User member = userRepository.findById(chat.getSenderId()).orElseThrow(() -> new NoSuchElementException("멤버가 없습니다."));

		//roomId를 통해 찾은 방에
		chat.setMessage(member.getName() + "님이 채팅방에 참여하였습니다.");
		//messagingTemplate.convertAndSend("/chat/" + roomId, chat);

		return chat;
	}

	/**
	 * 채팅 메시지 전송
	 * @param roomId
	 * @param chat
	 */
	@MessageMapping(value = "/chat/{roomId}")
	@SendTo("/room/send/receive/{roomId}")
	public ChatMessage sendMessage(@DestinationVariable("roomId") Long roomId, ChatMessageRequestDto chat) {

		ChatMessage message = new ChatMessage();
		message.setRoomId(roomId);
		message.setSenderId(chat.getSenderId());
		message.setMessage(chat.getMessage());
		chatMessageService.saveChatMessage(message);
		//messagingTemplate.convertAndSend("/chat/" + roomId, chat);
		log.info("메시지 = {}", message);
		return message;
	}
}
