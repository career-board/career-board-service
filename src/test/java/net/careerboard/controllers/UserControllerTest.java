package net.careerboard.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.careerboard.dto.UserResponse;
import net.careerboard.models.User;
import net.careerboard.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testHome() throws Exception {
        mockMvc.perform(get("/api/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json("{'message': 'Hello world'}"));
    }

    @Test
    public void testCreateUserSuccess() throws Exception {
        User user = new User();
        user.setCurrentCompany("Abc Inc.");
        user.setUsername("john.doe");

        UserResponse createdUser = new UserResponse();
        createdUser.setUserId(1L);
        createdUser.setCurrentCompany("Abc Inc.");
        createdUser.setUsername("john.doe");

        when(userService.addUser(any(User.class))).thenReturn(createdUser);

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(content().string("User is created successfully: " + createdUser.toString()));
    }

    @Test
    public void testCreateUserFailure() throws Exception {
        User user = new User();
        user.setCurrentCompany("Abc Inc.");
        user.setUsername("john.doe");

        when(userService.addUser(any(User.class))).thenThrow(new RuntimeException("User creation failed"));

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("User is creation failed"));
    }
}