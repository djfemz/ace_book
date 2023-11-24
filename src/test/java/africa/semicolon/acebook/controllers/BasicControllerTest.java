package africa.semicolon.acebook.controllers;


import africa.semicolon.acebook.dtos.request.UserRegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BasicControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegister() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UserRegisterRequest registerRequest = new UserRegisterRequest();
        registerRequest.setEmail("jon@email.com");
        registerRequest.setPassword("test");
        mockMvc.perform(post("/api/v1/account/basic")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(registerRequest)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

    }
}
