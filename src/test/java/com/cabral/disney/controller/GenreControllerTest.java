package com.cabral.disney.controller;

import com.cabral.disney.exception.GenreNotFoundException;
import com.cabral.disney.payload.request.GenreRequest;
import com.cabral.disney.payload.response.GenreResponse;
import com.cabral.disney.service.GenreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GenreController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class GenreControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    GenreService genreService;

    GenreRequest genreRequest;
    GenreRequest invalidGenreRequest;

    GenreResponse genreResponse;

    @BeforeEach
    public void init() {
        genreRequest = GenreRequest.builder().name("Romantic").build();
        invalidGenreRequest = GenreRequest.builder().name("INVALID NAME FOR A GENRE !!! 1!! 1").build();
        genreResponse = GenreResponse.builder().id(1L).name("Romantic").build();
    }

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
                .andExpect(jsonPath("$", hasSize(2)))
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
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getGenreByIdEndpointShouldReturn200_OK() throws Exception {
        ResultActions result = mockMvc.perform(get("/genres/genre/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    public void getGenreByNonExistentIdShouldReturn404_NOT_FOUND() throws Exception {

        when(this.genreService.getGenreById(anyLong())).thenThrow(GenreNotFoundException.class);

        ResultActions result = mockMvc.perform(get("/genres/genre/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldRetrieveByIdAndAssertGenreResponseBody() throws Exception {

        GenreResponse genreResponse = GenreResponse.builder().id(1L).name("Comedy").movieIds(new HashSet<>(Arrays.asList(1L, 2L, 3L))).build();

        when(this.genreService.getGenreById(anyLong())).thenReturn(genreResponse);

        ResultActions result = mockMvc.perform(get("/genres/genre/{id}", 1L))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Comedy"))
                .andExpect(jsonPath("$.movieIds", hasSize(3)));
    }

    @Test
    public void shouldHaveMessageWhenRetrievingByNonExistentId() throws Exception {
        Long nonExistentId = 1L;
        when(this.genreService.getGenreById(anyLong())).thenThrow(new GenreNotFoundException("Genre not found with id: " + nonExistentId));

        ResultActions result = mockMvc.perform(get("/genres/genre/{id}", nonExistentId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Genre not found with id: " + nonExistentId));
    }

    @Test
    public void shouldReturn201_CREATED_WhenCreatingAGenre() throws Exception {

        ResultActions result = mockMvc.perform(post("/genres/genre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(this.genreRequest))
                .characterEncoding("utf-8"));

        result.andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnGenreResponseOfTheCreatedGenre() throws Exception {

        when(this.genreService.createGenre(this.genreRequest)).thenReturn(this.genreResponse);

        ResultActions result = mockMvc.perform(post("/genres/genre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(this.genreRequest))
                .characterEncoding("utf-8"));

        result
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Romantic"));
    }

    @Test
    public void creatingGenreWithLongNameShouldReturnStatusCode400_BAD_REQUEST() throws Exception {

        ResultActions result = mockMvc.perform(post("/genres/genre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(this.invalidGenreRequest))
                .characterEncoding("utf-8"));

        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("The name of the genre must be between 3 and 15"));
    }

    @Test
    public void updatingGenreEndpointReturnsStatusCode200_OK() throws Exception {
        ResultActions result = mockMvc.perform(put("/genres/genre/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(this.genreRequest))
                .characterEncoding("utf-8"));

        result
                .andExpect(status().isOk());
    }

    @Test
    public void updateGenreWithNonExistentIdReturnsStatusCode404NotFoundAndMessage() throws Exception {
        Long nonExistentId = 1L;
        when(this.genreService.updateGenre(nonExistentId,this.genreRequest)).thenThrow(new GenreNotFoundException("Genre not found with id: " + nonExistentId));

        ResultActions result = mockMvc.perform(put("/genres/genre/{id}", nonExistentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(this.genreRequest))
                .characterEncoding("utf-8"));

        result
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Genre not found with id: " + nonExistentId));
    }
}
