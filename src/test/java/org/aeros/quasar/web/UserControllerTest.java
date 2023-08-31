package org.aeros.quasar.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.aeros.quasar.AbstractIT;
import org.aeros.quasar.core.domain.dto.RegisterDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AbstractIT {

    @Autowired
    private ObjectMapper mapper;

    @Test
    void testCanCreateNewUsers() throws Exception {
        var u1 = new RegisterDto("maelstrom", "maelstrom@aeros.org", "12345");
        mockMvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(u1)))
                .andExpect(status().is2xxSuccessful());
    }
}
