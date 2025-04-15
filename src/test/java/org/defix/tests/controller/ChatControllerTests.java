package org.defix.tests.controller;

import org.defix.tests.TestUtils;
import org.defix.user.exception.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.defix.database.postgresql.entity.Chat;
import org.defix.database.postgresql.entity.Message;
import org.defix.database.postgresql.entity.Role;
import org.defix.database.postgresql.entity.User;
import org.defix.chat.service.provider.Provider;
import org.defix.chat.exception.EmptyChatsException;
import org.defix.chat.api.dto.response.ChatApiDTO;
import org.defix.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ControllerTests
public class ChatControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private Provider<List<ChatApiDTO>, Integer> chatProvider;

    @Nested
    @DisplayName("Успешные тесты-провайдеры")
    class SuccessfulCases {
        @Test
        @WithMockUser(username = "user")
        public void getChatsTest() throws Exception {
            User user = new User(
                    1, "user", "", LocalDateTime.now(),
                    Collections.singleton(new Role(1, "USER")),
                    new HashSet<>()
            );
            User targetUser = new User(
                    2, "t_user", "", LocalDateTime.now(),
                    Collections.singleton(new Role(1, "USER")),
                    new HashSet<>()
            );
            Chat chat = new Chat();
            Message lastMessage = new Message(
                    2, "msg", targetUser, chat, LocalDateTime.now()
            );
            chat.setId(1);
            chat.setCreatedAt(LocalDateTime.now());
            chat.setMembers(Set.of(
                    user, targetUser
            ));
            chat.setMessages(Set.of(
                    new Message(
                            1, "msg___one", user, chat, LocalDateTime.now()
                    ),
                    lastMessage
            ));
            String expectedJson = STR."""
                [{
                 "targetUsername":"\{targetUser.getUsername()}",
                 "targetId":\{targetUser.getId()},
                 "lastMessage":"\{lastMessage.getMessage()}",
                 "localDateTime":"\{ TestUtils.localDateTimeToString(lastMessage.getCreatedAt())}"
                }]
                """;

            when(userService.findByUsername("user")).thenReturn(user);
            when(chatProvider.provide(user.getId())).thenReturn(List.of(
                    new ChatApiDTO(
                            targetUser.getUsername(),
                            targetUser.getId(),
                            lastMessage.getMessage(),
                            lastMessage.getCreatedAt()
                    )
            ));
            mockMvc.perform(get("/api/v1/chats")
                            .contentType("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedJson));
        }

        @Test
        @WithMockUser(username = "user")
        public void getChatInfoInfo() throws Exception {
            User user = new User(
                    2, "user", "", LocalDateTime.now(),
                    Collections.singleton(new Role(1, "USER")),
                    new HashSet<>()
            );
            when(userService.findById(2)).thenReturn(user);
            String expectedJson = STR."""
                {
                    "targetUsername":"\{user.getUsername()}",
                    "targetId":2
                }
                """;

            mockMvc.perform(get("/api/v1/chats/metadata")
                            .param("targetUserId", "2"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedJson));
        }
    }

    @Nested
    @DisplayName("Тесты на проверку пустых данных")
    class NotFoundErrorCases {
        @Test
        @WithMockUser(username = "user")
        public void getChatsIfChatsNotFoundTest() throws Exception {
            User user = new User(
                    1, "user", "", LocalDateTime.now(),
                    Collections.singleton(new Role(1, "USER")),
                    new HashSet<>()
            );
            when(userService.findByUsername("user")).thenReturn(user);
            when(chatProvider.provide(1)).thenThrow(new EmptyChatsException());
            mockMvc.perform(get("/api/v1/chats")
                            .contentType("application/json"))
                    .andExpect(status().isNotFound());
        }

        @Test
        @WithMockUser(username = "user")
        public void getChatsIfUserNotFoundTest() throws Exception {
            when(userService.findByUsername("user")).thenThrow(new UserNotFoundException());
            mockMvc.perform(get("/api/v1/chats")
                            .contentType("application/json"))
                    .andExpect(status().isNotFound());
        }

        @Test
        @WithMockUser
        public void getChatInfoIfUserNotFoundTest() throws Exception {
            when(userService.findById(1)).thenThrow(new UserNotFoundException());
            mockMvc.perform(get("/api/v1/chats/metadata")
                            .param("targetUserId", "1")
                            .contentType("application/json"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("Тесты на проверку ошибок авторизации")
    class AuthErrorCases {
        @Test
        public void getChatsIfPrincipalNullTest() throws Exception {
            mockMvc.perform(get("/api/v1/chats")
                            .contentType("application/json"))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    class ParamErrorCases {
        @Test
        @WithMockUser
        public void getChatInfoIfParamNullTest() throws Exception {
            mockMvc.perform(get("/api/v1/chats/metadata")
                    .contentType("application/json"))
                    .andExpect(status().isBadRequest());
        }
    }
}
