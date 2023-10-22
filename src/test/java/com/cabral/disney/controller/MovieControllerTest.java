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
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    MockPart filePart;
    MockPart jsonPart;

    @BeforeEach
    public void init() throws IOException {
        fechaCreacion = LocalDate.of(1992, 8, 12);
        movieRequest = MovieRequest.builder().title("Aladdin").creationDate(fechaCreacion).synopsis("Princess Jasmine grows tired of being forced to remain in the palace, so she sneaks out into the marketplace, in disguise, where she meets street-urchin Aladdin. The couple falls in love, although Jasmine may only marry a prince. After being thrown in jail, Aladdin becomes embroiled in a plot to find a mysterious lamp, with which the evil Jafar hopes to rule the land.").build();
        Path path = Paths.get("src/test/resources/image/jafar.jpg");
        byte[] imageBytes = Files.readAllBytes(path);

        String jsonMovieRequest = objectMapper.writeValueAsString(movieRequest);

        filePart = new MockPart("image", "name", imageBytes);
        jsonPart = new MockPart("movieRequest", "movieRequest", jsonMovieRequest.getBytes());
        jsonPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);
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

        jsonPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ResultActions response = mockMvc.perform(multipart("/movies/movie")
                .part(filePart)
                .part(jsonPart)
        );

        response.andExpect(status().isCreated());
    }

    @Test
    public void testUpdateMovieEndpointAndResponseIs200_OK() throws Exception {

        ResultActions response = mockMvc.perform(multipart(HttpMethod.PUT, "/movies/movie/{id}", 1L)
                .part(filePart)
                .part(jsonPart)
        );

        response.andExpect(status().isOk());
    }

    @Test
    public void testUpdateMovieEndpointAndResponseIs404_NOT_FOUND() throws Exception {
        when(this.movieService.updateMovie(anyLong(), eq(this.movieRequest), any(MockMultipartFile.class))).thenThrow(new MovieNotFoundException("Movie Not Found"));

        ResultActions response = mockMvc.perform(multipart(HttpMethod.PUT, "/movies/movie/{id}", 1L)
                .part(filePart)
                .part(jsonPart)
        );

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Movie Not Found"));
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
