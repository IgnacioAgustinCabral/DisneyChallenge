package com.cabral.disney.controller;

import com.cabral.disney.exception.MovieNotFoundException;
import com.cabral.disney.exception.MovieSearchEmptyResultException;
import com.cabral.disney.payload.request.MovieRequest;
import com.cabral.disney.service.MovieService;
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

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MovieController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class MovieControllerTest {

    private LocalDate fechaCreacion;
    private MovieRequest movieRequest;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MovieService movieService;

    @BeforeEach
    public void init() {
        fechaCreacion = LocalDate.of(2023, 8, 12);
        movieRequest = MovieRequest.builder().titulo("TITULO").fecha_creacion(fechaCreacion).imagen("path/to/image").calificacion(5).build();
    }

    @Test
    public void testGetAllMoviesEndpointAndResponseIs200_OK() throws Exception {
        ResultActions response = mockMvc.perform(get("/movies/movie/all"));

        response.andExpect(status().isOk());
    }

    @Test
    public void testGetMovieEndpointAndResponseIs200_OK() throws Exception {

        ResultActions response = mockMvc.perform(get("/movies/movie/{id}", 1L));

        response.andExpect(status().isOk());
    }

    @Test
    public void testGetMovieEndpointAndResponseIs404_NOT_FOUND() throws Exception {
        when(this.movieService.getMovieById(anyLong())).thenThrow(MovieNotFoundException.class);

        ResultActions response = mockMvc.perform(get("/movies/movie/{id}", 1L));

        response.andExpect(status().isNotFound());
    }

    @Test
    public void testCreateMovieEndpointAndResponseIs201_CREATED() throws Exception {

        ResultActions response = mockMvc.perform(post("/movies/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(this.movieRequest))
                .characterEncoding("utf-8"));

        response.andExpect(status().isCreated());
//                response.andExpect(jsonPath("$.titulo").value("asd"));

    }

    @Test
    public void testUpdateMovieEndpointAndResponseIs200_OK() throws Exception {

        ResultActions response = mockMvc.perform(put("/movies/movie/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(this.movieRequest))
                .characterEncoding("utf-8"));

        response.andExpect(status().isOk());
    }

    @Test
    public void testUpdateMovieEndpointAndResponseIs404_NOT_FOUND() throws Exception {
        when(this.movieService.updateMovie(anyLong(), eq(this.movieRequest))).thenThrow(MovieNotFoundException.class);

        ResultActions response = mockMvc.perform(put("/movies/movie/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(this.movieRequest))
                .characterEncoding("utf-8"));

        response.andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteMovieEndpointAndResponseIs204_NO_CONTENT() throws Exception {

        ResultActions response = mockMvc.perform(delete("/movies/movie/{id}", 1L));

        response.andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteMovieEndpointAndResponseIs404_NOT_FOUND() throws Exception {
        doThrow(MovieNotFoundException.class).when(this.movieService).deleteMovie(anyLong());

        ResultActions response = mockMvc.perform(delete("/movies/movie/{id}", 1L));

        response.andExpect(status().isNotFound());
    }

    @Test
    public void testSearchMovieEndpointAndResponseIs200_OK() throws Exception {

        ResultActions response = mockMvc.perform(get("/movies/movie")
                .param("name", "example"));

        response.andExpect(status().isOk());
    }

    @Test
    public void testSearchMovieEndpointAndResponseIs404_NOT_FOUND() throws Exception {
        when(this.movieService.searchMovie(anyString())).thenThrow(MovieSearchEmptyResultException.class);

        ResultActions response = mockMvc.perform(get("/movies/movie")
                .param("name", "example"));

        response.andExpect(status().isNotFound());
    }
}
