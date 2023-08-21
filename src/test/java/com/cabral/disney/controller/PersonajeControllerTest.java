package com.cabral.disney.controller;

import com.cabral.disney.dto.PersonajeDTO;
import com.cabral.disney.exception.PersonajeNotFoundException;
import com.cabral.disney.service.PersonajeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(PersonajeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PersonajeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonajeService personajeService;

    @InjectMocks
    private PersonajeController personajeController;


    @Test
    public void testGetAllPersonajesEndpointAndResponseIs200_OK() throws Exception {

        ResultActions response = mockMvc.perform(get("/personajes/personaje/all")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetPersonajeEndpointAndResponseIs200_OK() throws Exception {

        PersonajeDTO personajeDTO = new PersonajeDTO();

        ResultActions response = mockMvc.perform(get("/personajes/personaje/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetPersonajeEndpointAndResponseIs404_NOT_FOUND() throws Exception {

        when(this.personajeService.getPersonajeById(anyLong())).thenThrow(PersonajeNotFoundException.class);

        ResultActions response = mockMvc.perform(get("/personajes/personaje/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testUpdatePersonajeEndpointAndResponseIs200_OK() throws Exception {
        PersonajeDTO updatedPersonajeDTO = new PersonajeDTO();

        ResultActions response = mockMvc.perform(put("/personajes/personaje/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedPersonajeDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdatePersonajeEndpointAndResponseIs404_NOT_FOUND() throws Exception {
        PersonajeDTO updatedPersonajeDTO = new PersonajeDTO();

        when(this.personajeService.updatePersonaje(anyLong(), eq(updatedPersonajeDTO))).thenThrow(PersonajeNotFoundException.class);

        ResultActions response = mockMvc.perform(put("/personajes/personaje/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedPersonajeDTO)));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testCreatePersonajeEndpointAndResponseIs201_CREATED() throws Exception {

        PersonajeDTO personajeDTO = new PersonajeDTO();

        ResultActions response = mockMvc.perform(post("/personajes/personaje")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personajeDTO)));

        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testDeletePersonajeEndpointAndResponseIs204_NO_CONTENT() throws Exception {

        ResultActions response = mockMvc.perform(delete("/personajes/personaje/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testDeletePersonajeEndpointAndResponseIs404_NOT_FOUND() throws Exception {

        doThrow(PersonajeNotFoundException.class).when(this.personajeService).deletePersonaje(anyLong());

        ResultActions response = mockMvc.perform(delete("/personajes/personaje/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testSearchPersonajEndpointAndResponseIs200_OK() throws Exception {
        ResultActions response = mockMvc.perform(get("/personajes/personaje/")
                .param("name","example")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}

