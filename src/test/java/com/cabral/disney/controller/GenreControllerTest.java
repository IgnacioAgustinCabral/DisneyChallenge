package com.cabral.disney.controller;

import com.cabral.disney.service.GenreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = GenreController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class GenreControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    GenreService genreService;

    @Test
    public void getAllGenresEndpointShouldReturn200_OK() throws Exception {

        ResultActions result = mockMvc.perform(get("/genres/genre/all"))
                .andExpect(status().isOk());
    }

}
