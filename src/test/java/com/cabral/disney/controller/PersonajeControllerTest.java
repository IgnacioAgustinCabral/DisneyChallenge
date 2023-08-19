package com.cabral.disney.controller;

import com.cabral.disney.dto.PersonajeDTO;
import com.cabral.disney.exception.PersonajeNotFoundException;
import com.cabral.disney.service.PersonajeService;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(PersonajeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PersonajeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonajeService personajeService;

    @InjectMocks
    private PersonajeController personajeController;


    @Test
    public void testGetAllPersonajesEndpointAndResponseIs200_OK() throws Exception {
        List<PersonajeDTO> personajeDTOs = Arrays.asList(new PersonajeDTO());

        when(this.personajeService.getAllPersonajes()).thenReturn(personajeDTOs);
        ResultActions response = mockMvc.perform(get("/personajes/personaje/all")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetPersonajeEndpointAndResponseIs200_OK() throws Exception {

        PersonajeDTO personajeDTO = new PersonajeDTO();
        when(this.personajeService.getPersonajeById(anyLong())).thenReturn(personajeDTO);

        ResultActions response = mockMvc.perform(get("/personajes/personaje/{id}",1)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetPersonajeEndpointAndResponseIs404_NOT_FOUND() throws Exception {

        when(this.personajeService.getPersonajeById(anyLong())).thenThrow(PersonajeNotFoundException.class);

        ResultActions response = mockMvc.perform(get("/personajes/personaje/{id}",1)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

