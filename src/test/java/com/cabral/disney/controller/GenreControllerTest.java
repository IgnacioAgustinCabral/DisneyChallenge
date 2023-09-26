package com.cabral.disney.controller;

import com.cabral.disney.exception.GenreNotFoundException;
import com.cabral.disney.payload.response.GenreResponse;
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

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @Test
    public void shouldReturnTwoGenreResponsesWhenFetchingAllGenres() throws Exception {

        List<GenreResponse> genreResponses = Arrays.asList(
                GenreResponse.builder().id(1L).name("Comedy").build(),
                GenreResponse.builder().id(2L).name("Action").build()
        );

        when(this.genreService.getAllGenres()).thenReturn(genreResponses);

        ResultActions result = mockMvc.perform(get("/genres/genre/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$",hasSize(2)))
                //Asserts each element in the JSON array
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Comedy"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Action"));
    }

    @Test
    public void shouldReturnEmptyJsonArrayWhenThereAreNoGenres() throws Exception {

        when(this.genreService.getAllGenres()).thenReturn(List.of());

        ResultActions result = mockMvc.perform(get("/genres/genre/all"))
                .andExpect(jsonPath("$",hasSize(0)));
    }

    @Test
    public void getGenreByIdEndpointShouldReturn200_OK() throws Exception {
        ResultActions result = mockMvc.perform(get("/genres/genre/{id}",1L))
                .andExpect(status().isOk());
    }

    @Test
    public void getGenreByNonExistenIdShouldReturn404_NOT_FOUND() throws Exception {

        when(this.genreService.getGenreById(anyLong())).thenThrow(GenreNotFoundException.class);

        ResultActions result = mockMvc.perform(get("/genres/genre/{id}",1L))
                .andExpect(status().isNotFound());
    }
}
