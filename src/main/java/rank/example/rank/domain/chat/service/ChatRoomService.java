package rank.example.rank.domain.chat.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import rank.example.rank.domain.chat.dto.GetChatRoomResponseDto;
import rank.example.rank.domain.chat.entity.ChatRoom;
import rank.example.rank.domain.chat.repository.ChatRoomRepository;
import rank.example.rank.domain.user.entity.User;
import rank.example.rank.domain.user.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {

	final private ChatRoomRepository chatRoomRepository;
	final private ChatMessageService chatMessageService;
	private final UserRepository userRepository;

	@Transactional
	public ChatRoom createChatRoom(Long senderId, Long inviteeId) {
		ChatRoom chatRoom = new ChatRoom(senderId, inviteeId);
		return chatRoomRepository.save(chatRoom);
	}

	public ChatRoom findById(Long roomId) {
		return chatRoomRepository.findById(roomId)
			.orElseThrow(() -> new NoSuchElementException("채팅방을 찾을 수 없습니다."));
	}

	public List<GetChatRoomResponseDto> findChatRoomId(Long id) {
		List<ChatRoom> chatRooms = chatRoomRepository.findAllBySenderIdOrInviteeId(id);
		return chatRooms.stream()
				.map(chatRoom -> convertToGetChatRoomResponse(chatRoom, id))
				.collect(Collectors.toList());
	}

	@Transactional
	public boolean deleteChatRoom(Long roomId) {
		if (chatRoomRepository.existsById(roomId)) {
			chatRoomRepository.deleteById(roomId);
			chatMessageService.deleteChatDataByInMySQL(roomId);
			return true;
		} else {
			return false;
		}
	}

	private GetChatRoomResponseDto convertToGetChatRoomResponse(ChatRoom chatRoom, Long userId) {
		GetChatRoomResponseDto responseDto = new GetChatRoomResponseDto();
		responseDto.setRoomId(chatRoom.getId());
		responseDto.setSenderId(chatRoom.getSenderId());
		responseDto.setInviteeId(chatRoom.getInviteeId());
        User user;
        if (userId == chatRoom.getSenderId()) {
            user = userRepository.findById(chatRoom.getInviteeId()).orElseThrow();
        } else {
            user = userRepository.findById(chatRoom.getSenderId()).orElseThrow();
        }
        responseDto.setOpponentId(user.getId());
        responseDto.setOpponentName(user.getName());
        responseDto.setImageUrl(user.getProfileImageUrl());
//        responseDto.setImageUrl(userRepository.findById(userId).orElseThrow().getProfileImageUrl());

		return responseDto;
	}
}
