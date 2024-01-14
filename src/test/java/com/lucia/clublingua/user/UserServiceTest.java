package com.lucia.clublingua.user;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testSaveUser() {
        // Arrange
        User user = new User();
        user.setNickName("testUser");

        // Act
        userService.saveUser(user);

        // Assert
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        assertEquals(Status.ONLINE, user.getStatus());
    }

    @Test
    void testDisconnectUser() {
        // Arrange
        User user = new User();
        user.setNickName("testUser");
        user.setStatus(Status.ONLINE);

        Mockito.when(userRepository.findById(user.getNickName())).thenReturn(Optional.of(user));

        // Act
        userService.disconnect(user);

        // Assert
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        assertEquals(Status.OFFLINE, user.getStatus());
    }

    @Test
    void testDisconnectNonExistingUser() {
        // Arrange
        User user = new User();
        user.setNickName("nonExistingUser");

        Mockito.when(userRepository.findById(user.getNickName())).thenReturn(Optional.empty());

        // Act
        userService.disconnect(user);

        // Assert
        // Verify that save is not called for non-existing user
        Mockito.verify(userRepository, Mockito.never()).save(user);
    }

    @Test
    void testFindConnectedUsers() {
        // Arrange
        User connectedUser = new User();
        connectedUser.setNickName("connectedUser");
        connectedUser.setStatus(Status.ONLINE);

        User offlineUser = new User();
        offlineUser.setNickName("offlineUser");
        offlineUser.setStatus(Status.OFFLINE);

        Mockito.when(userRepository.findAllByStatus(Status.ONLINE)).thenReturn(Collections.singletonList(connectedUser));

        // Act
        List<User> connectedUsers = userService.findConnectedUsers();

        // Assert
        assertEquals(1, connectedUsers.size());
        assertEquals("connectedUser", connectedUsers.get(0).getNickName());
    }
}