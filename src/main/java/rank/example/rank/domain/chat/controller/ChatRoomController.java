package rank.example.rank.domain.chat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import rank.example.rank.domain.chat.dto.ChatRoomResponseDto;
import rank.example.rank.domain.chat.dto.CreateChatRoomDto;
import rank.example.rank.domain.chat.entity.ChatRoom;
import rank.example.rank.domain.chat.service.ChatMessageService;
import rank.example.rank.domain.chat.service.ChatRoomService;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

	private final ChatRoomService chatRoomService;
	private final ChatMessageService chatMessageService;

	/**
	 * 채팅방 삭제
	 * @param createChatRoomDto
	 * @return
	 */
	@PostMapping("/chatroom")
	public ResponseEntity<ChatRoomResponseDto> createChatRoom(@RequestBody CreateChatRoomDto createChatRoomDto) {

		ChatRoom chatRoom = chatRoomService.createChatRoom(createChatRoomDto.getSenderId(),
			createChatRoomDto.getInviteeId());

		chatMessageService.sendJoinMessage(chatRoom.getId(), createChatRoomDto.getSenderId());

		ChatRoomResponseDto chatRoomResponseDto = new ChatRoomResponseDto();
		chatRoomResponseDto.setChatroomId(chatRoom.getId());

		return ResponseEntity.ok(chatRoomResponseDto);
	}

	/**
	 * roomId로 채팅방, redis, mysql에서의 채팅기록 삭제
	 * @param roomId
	 * @return
	 */
	@DeleteMapping("/chatroom/{roomId}")
	public ResponseEntity<Boolean> deleteChatRoom(@PathVariable Long roomId) {
		if(chatRoomService.deleteChatRoom(roomId))
			return ResponseEntity.ok(true);
		else
			return ResponseEntity.ok(false);
	}
}
