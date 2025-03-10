package org.defix.tests.controllers;

import org.defix.tests.TestUtils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.defix.database.postgresql.entities.Role;
import org.defix.database.postgresql.entities.User;
import org.defix.services.chat.abstractions.BiProvider;
import org.defix.services.chat.exceptions.NoMessagesException;
import org.defix.services.chat.objects.MessageApiDTO;
import org.defix.services.user.UserService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ControllerTests
public class MessageControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private BiProvider<List<MessageApiDTO>, Integer> messageProvider;

    @Nested
    class SuccessfulCases {
        @WithMockUser(username = "user")
        @Test
        public void getMessagesTest() throws Exception {
            User user = new User(
                    1, "user", "", LocalDateTime.now(), Collections.singleton(new Role(1, "USER")), new HashSet<>()
            );
            MessageApiDTO messageApiDTO = new MessageApiDTO(
                    "msg", LocalDateTime.now(), 2
            );

            String expectedJson = STR."""
                [{
                 "message":"\{messageApiDTO.getMessage()}",
                 "createdAt":"\{TestUtils.localDateTimeToString(messageApiDTO.getCreatedAt())}",
                 "senderId":\{messageApiDTO.getSenderId()}
                }]
                """;

            when(userService.findByUsername("user")).thenReturn(user);
            when(messageProvider.provide(user.getId(), 2)).thenReturn(List.of(messageApiDTO));
            mockMvc.perform(get("/api/v1/messages")
                            .param("targetUserId", "2")
                            .contentType("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedJson));
        }
    }

    @Nested
    class ParamErrorCases {
        @Test
        @WithMockUser
        public void getMessagesIfParamNull() throws Exception {
            mockMvc.perform(get("/api/v1/messages")
                            .contentType("application/json"))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class AuthErrorCases {
        @Test
        public void getMessagesIfPrincipalNullTest() throws Exception {
            mockMvc.perform(get("/api/v1/messages")
                            .param("targetUserId", "1")
                            .contentType("application/json"))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    class NotFoundErrorCases {
        @Test
        @WithMockUser(username = "user")
        public void getMessagesIfEmptyTest() throws Exception {
            User user = new User(
                    1, "user", "", LocalDateTime.now(), Collections.singleton(new Role(1, "USER")), new HashSet<>()
            );

            when(userService.findByUsername("user")).thenReturn(user);
            when(messageProvider.provide(user.getId(), 2)).thenThrow(new NoMessagesException());
            mockMvc.perform(get("/api/v1/messages")
                            .param("targetUserId", "2")
                            .contentType("application/json"))
                    .andExpect(status().isNotFound());
        }
    }
}
