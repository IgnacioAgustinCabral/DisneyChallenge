package com.cabral.disney.controller;

import com.cabral.disney.exception.CharacterNotFoundException;
import com.cabral.disney.exception.CharacterSearchEmptyResultException;
import com.cabral.disney.payload.request.CharacterRequest;
import com.cabral.disney.service.CharacterService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CharacterController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CharacterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CharacterService characterService;

    @InjectMocks
    private CharacterController characterController;


    @Test
    public void testGetAllCharactersEndpointAndResponseIs200_OK() throws Exception {

        ResultActions response = mockMvc.perform(get("/characters/character/all"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetCharacterEndpointAndResponseIs200_OK() throws Exception {

        ResultActions response = mockMvc.perform(get("/characters/character/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetCharacterEndpointAndResponseIs404_NOT_FOUND() throws Exception {

        when(this.characterService.getCharacterById(anyLong())).thenThrow(CharacterNotFoundException.class);

        ResultActions response = mockMvc.perform(get("/characters/character/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testUpdateCharacterEndpointAndResponseIs200_OK() throws Exception {
        CharacterRequest characterRequest = CharacterRequest.builder().name("Aladdin").age(22).weight(61.3).history("HISTORIAXHISTORIAXHISTORIAXXXXX").build();

        ResultActions response = mockMvc.perform(put("/characters/character/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(characterRequest)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateCharacterEndpointAndResponseIs404_NOT_FOUND() throws Exception {
        CharacterRequest characterRequest = CharacterRequest.builder().name("Aladdin").age(22).weight(61.3).history("HISTORIAXHISTORIAXHISTORIAXXXXX").build();

        when(this.characterService.updateCharacter(anyLong(), eq(characterRequest))).thenThrow(CharacterNotFoundException.class);

        ResultActions response = mockMvc.perform(put("/characters/character/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(characterRequest)));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testUpdateCharacterEndpointAndResponseIs404_BAD_REQUEST() throws Exception {
        //INVALID WEIGHT PESO
        CharacterRequest characterRequest = CharacterRequest.builder().name("Aladdin").age(22).weight(0.001).history("HISTORIAXHISTORIAXHISTORIAXXXXX").build();

        when(this.characterService.updateCharacter(anyLong(), eq(characterRequest))).thenThrow(CharacterNotFoundException.class);

        ResultActions response = mockMvc.perform(put("/characters/character/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(characterRequest)));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testCreateCharacterEndpointAndResponseIs201_CREATED() throws Exception {

        CharacterRequest characterRequest = CharacterRequest.builder().name("Aladdin").age(22).weight(61.3).history("HISTORIAXHISTORIAXHISTORIAXXXXX").build();

        ResultActions response = mockMvc.perform(post("/characters/character")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(characterRequest)));

        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testCreateCharacterEndpointAndResponseIs400_BAD_REQUEST() throws Exception {
        //INVALID WEIGHT PESO
        CharacterRequest invalidCharacterRequest = CharacterRequest.builder().name("Aladdin").age(22).weight(0.001).history("HISTORIAXHISTORIAXHISTORIAXXXXX").build();

        ResultActions response = mockMvc.perform(post("/characters/character")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidCharacterRequest)));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testDeleteCharacterEndpointAndResponseIs204_NO_CONTENT() throws Exception {

        ResultActions response = mockMvc.perform(delete("/characters/character/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testDeleteCharacterEndpointAndResponseIs404_NOT_FOUND() throws Exception {

        doThrow(CharacterNotFoundException.class).when(this.characterService).deleteCharacter(anyLong());

        ResultActions response = mockMvc.perform(delete("/characters/character/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testSearchCharacterEndpointAndResponseIs200_OK() throws Exception {
        ResultActions response = mockMvc.perform(get("/characters/character/")
                .param("name", "example")
                .param("age","1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testSearchCharacterEndpointAndResponseIs404_NOT_FOUND() throws Exception {

        when(this.characterService.searchCharacter(anyString(), anyInt())).thenThrow(CharacterSearchEmptyResultException.class);

        ResultActions response = mockMvc.perform(get("/characters/character")
                .param("name", "something")
                .param("age","1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

