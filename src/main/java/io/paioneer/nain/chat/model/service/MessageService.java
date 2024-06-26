package io.paioneer.nain.chat.model.service;

import io.paioneer.nain.chat.jpa.entity.ChatRoomEntity;
import io.paioneer.nain.chat.jpa.entity.MessageEntity;
import io.paioneer.nain.chat.jpa.repository.chatmessage.MessageRepository;
import io.paioneer.nain.chat.jpa.repository.chatroom.ChatRoomRepository;
import io.paioneer.nain.chat.model.dto.ChatRoomDto;
import io.paioneer.nain.chat.model.dto.MessageDto;
import io.paioneer.nain.member.jpa.entity.MemberEntity;
import io.paioneer.nain.member.jpa.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    public void saveMessage(MessageDto messageDto) {
        ChatRoomEntity chatRoom = chatRoomRepository.findById(messageDto.getChatRoomNo())
                .orElseThrow(() -> new RuntimeException("ChatRoom not found"));
        MemberEntity memberEntity = memberRepository.findById(messageDto.getMemberNo())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        MessageEntity message = messageDto.toEntity(chatRoom, memberEntity);

        messageRepository.save(message);
    }

    public List<MessageDto> getMessagesByRoomId(Long roomId) {
        List<MessageEntity> messages = messageRepository.findByChatRoom_ChatRoomNo(roomId, Sort.by(Sort.Direction.ASC, "messageDate"));
        return messages.stream()
                       .map(MessageDto::fromEntity)
                       .collect(Collectors.toList());
    }

    public List<ChatRoomDto> getAllRooms() {
        List<ChatRoomEntity> chatRooms = chatRoomRepository.findAll();
        return chatRooms.stream()
                        .map(ChatRoomDto::fromEntity)
                        .collect(Collectors.toList());
    }
}
