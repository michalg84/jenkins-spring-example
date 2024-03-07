package com.galka.jenkinsspringexample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ControllerTest {


    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testCheck() throws Exception {
        this.mockMvc.perform(get("/check"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("It works!")
                );
    }

    @Test
    void testLogin() throws Exception {
        String body = toJson(new LoginUserRequest("admin", "admin"));
        this.mockMvc.perform(post("/user/login")
                        .content(body)
                        .header(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("User not found"));
    }

@Test
    void testCreate() throws Exception {
        String body = toJson(new CreateUserRequest("mig", "123asd"));
        this.mockMvc.perform(put("/user/create")
                        .content(body)
                        .header(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("User created"));
    }

    @Test
    void testErrors() throws Exception {
        String body = toJson(new LoginUserRequest(null, null));
        this.mockMvc.perform(put("/user/login")
                        .content(body)
                        .header(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    private String toJson(Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }
}
