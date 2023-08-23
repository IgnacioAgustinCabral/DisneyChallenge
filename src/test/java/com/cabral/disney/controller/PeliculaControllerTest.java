package com.cabral.disney.controller;

import com.cabral.disney.service.PeliculaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(PeliculaController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PeliculaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PeliculaService peliculaService;
    

    @Test
    public void testGetAllPeliculasEndpointAndResponseIs200_OK() throws Exception {
        ResultActions response = mockMvc.perform(get("/peliculas/pelicula/all"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetPeliculaEndpointAndResponseIs200_OK() throws Exception {

        ResultActions response = mockMvc.perform(get("/peliculas/pelicula/{id}",1));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
