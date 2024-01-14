package com.lucia.clublingua.chat;

import com.lucia.clublingua.chatroom.ChatRoomService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ChatMessageServiceTest {

    @Mock
    private ChatMessageRepository repository;

    @Mock
    private ChatRoomService chatRoomService;

    @InjectMocks
    private ChatMessageService chatMessageService;

    @Test
    void testSave() {
        //Arrange
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSenderId("senderId");
        chatMessage.setRecipientId("recipientId");

        Mockito.when(chatRoomService.getChatRoomId("senderId", "recipientId", true))
                .thenReturn(Optional.of("chatId"));

        //Act
        chatMessageService.save(chatMessage);

        // Asserts
        Mockito.verify(repository, Mockito.times(1)).save(chatMessage);
    }

    @Test
    void testFindChatMessages() {
        //Arrange
        String senderId = "senderId";
        String recipientId = "recipientId";

        Mockito.when(chatRoomService.getChatRoomId(senderId, recipientId, false))
                .thenReturn(Optional.of("chatId"));

        List<ChatMessage> expectedChatMessages = new ArrayList<>();

        Mockito.when(repository.findByChatId("chatId")).thenReturn(expectedChatMessages);

        //Act
        List<ChatMessage> result = chatMessageService.findChatMessages(senderId, recipientId);

        //Assets
        assertEquals(expectedChatMessages, result);
    }
}