package uin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import uin.dto.AccessTokenDTO;
import uin.dto.LoginDTO;
import uin.dto.RegisterDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService service;

    @Test
    @Order(1)
    void shouldRegisterNewAccount() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("joko", "joko@123", "joko@mail.com");

        mockMvc.perform(post("/auth/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isAccepted());
    }

    @Test
    @Order(2)
    void shouldLoginAndReturnAccessToken() throws Exception {
        // Assuming a user with username "joko" and password "joko@123" is already registered
        LoginDTO loginDTO = new LoginDTO("joko", "joko@123");

        var result = mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        AccessTokenDTO accessTokenDTO = objectMapper.readValue(content, AccessTokenDTO.class);

        assertThat(accessTokenDTO).isNotNull();
    }
}