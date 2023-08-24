package com.cabral.disney.controller;

import com.cabral.disney.dto.PeliculaDTO;
import com.cabral.disney.exception.PeliculaNotFoundException;
import com.cabral.disney.service.PeliculaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
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

@WebMvcTest(controllers = PeliculaController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PeliculaControllerTest {

    private LocalDate fechaCreacion;
    private PeliculaDTO peliculaDTO;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PeliculaService peliculaService;

    @BeforeEach
    public void init() {
        fechaCreacion = LocalDate.of(2023, 8, 12);
        peliculaDTO = new PeliculaDTO(1L, "asd", fechaCreacion, 1, "imagen");
    }

    @Test
    public void testGetAllPeliculasEndpointAndResponseIs200_OK() throws Exception {
        ResultActions response = mockMvc.perform(get("/peliculas/pelicula/all"));

        response.andExpect(status().isOk());
    }

    @Test
    public void testGetPeliculaEndpointAndResponseIs200_OK() throws Exception {

        ResultActions response = mockMvc.perform(get("/peliculas/pelicula/{id}", 1L));

        response.andExpect(status().isOk());
    }

    @Test
    public void testGetPeliculaEndpointAndResponseIs404_NOT_FOUND() throws Exception {
        when(this.peliculaService.getPeliculaById(anyLong())).thenThrow(PeliculaNotFoundException.class);

        ResultActions response = mockMvc.perform(get("/peliculas/pelicula/{id}", 1L));

        response.andExpect(status().isNotFound());
    }

    @Test
    public void testCreatePeliculaEndpointAndResponseIs201_CREATED() throws Exception {

        ResultActions response = mockMvc.perform(post("/peliculas/pelicula")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(this.peliculaDTO))
                .characterEncoding("utf-8"));

        response.andExpect(status().isCreated());
//                response.andExpect(jsonPath("$.titulo").value("asd"));

    }

    @Test
    public void testUpdatePeliculaEndpointAndResponseIs200_OK() throws Exception {

        ResultActions response = mockMvc.perform(put("/peliculas/pelicula/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(this.peliculaDTO))
                .characterEncoding("utf-8"));

        response.andExpect(status().isOk());
    }

    @Test
    public void testUpdatePeliculaEndpointAndResponseIs404_NOT_FOUND() throws Exception {
        when(this.peliculaService.updatePelicula(anyLong(), eq(this.peliculaDTO))).thenThrow(PeliculaNotFoundException.class);

        ResultActions response = mockMvc.perform(put("/peliculas/pelicula/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(this.peliculaDTO))
                .characterEncoding("utf-8"));

        response.andExpect(status().isNotFound());
    }

    @Test
    public void testDeletePeliculaEndpointAndResponseIs204_NO_CONTENT() throws Exception {

        ResultActions response = mockMvc.perform(delete("/peliculas/pelicula/{id}", 1L));

        response.andExpect(status().isNoContent());
    }

    @Test
    public void testDeletePeliculaEndpointAndResponseIs404_NOT_FOUND() throws Exception {
        doThrow(PeliculaNotFoundException.class).when(this.peliculaService).deletePelicula(anyLong());

        ResultActions response = mockMvc.perform(delete("/peliculas/pelicula/{id}", 1L));

        response.andExpect(status().isNotFound());
    }

    @Test
    public void testSearchPeliculaEndpointAndResponseIs200_OK() throws Exception {

        ResultActions response = mockMvc.perform(get("/peliculas/pelicula")
                .param("name","example"));

        response.andExpect(status().isOk());
    }
}
