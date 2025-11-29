package com.badkul.backendintern;

import com.badkul.backendintern.controller.OrganizerController;
import com.badkul.backendintern.dto.*;
import com.badkul.backendintern.service.OrganizerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrganizerController.class)
public class OrganizerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrganizerService service;

    @Test
    void createOrganizer_success() throws Exception {
        OrganizerCreateRequest req = OrganizerCreateRequest.builder()
                .name("Badkul")
                .email("a@b.com")
                .phone("9999999999")
                .businessType("Events").build();

        OrganizerResponse res = OrganizerResponse.builder()
                .id(1L).organizerCode("ORG00001").name("Badkul").email("a@b.com").phone("9999999999").build();

        when(service.createOrganizer(any())).thenReturn(res);

        mockMvc.perform(post("/api/organizers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Badkul\",\"email\":\"a@b.com\",\"phone\":\"9999999999\",\"businessType\":\"Events\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.organizerCode").value("ORG00001"));
    }
}
