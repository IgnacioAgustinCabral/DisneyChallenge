package com.cabral.disney.controller;

import com.cabral.disney.exception.CharacterNotFoundException;
import com.cabral.disney.exception.CharacterSearchEmptyResultException;
import com.cabral.disney.payload.request.CharacterRequest;
import com.cabral.disney.service.CharacterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CharacterController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CharacterControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CharacterService characterService;

    @Test
    public void testGetAllCharactersEndpointAndResponseIs200_OK() throws Exception {

        ResultActions response = mockMvc.perform(get("/characters/character/all"));

        response.andExpect(status().isOk());
    }

    @Test
    public void testGetCharacterEndpointAndResponseIs200_OK() throws Exception {

        ResultActions response = mockMvc.perform(get("/characters/character/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());
    }

    @Test
    public void testGetCharacterEndpointAndResponseIs404_NOT_FOUND() throws Exception {

        when(this.characterService.getCharacterById(anyLong())).thenThrow(CharacterNotFoundException.class);

        ResultActions response = mockMvc.perform(get("/characters/character/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateCharacterEndpointAndResponseIs200_OK() throws Exception {
        Path path = Paths.get("src/test/resources/image/jafar.jpg");
        byte[] imageBytes = Files.readAllBytes(path);
        MockPart filePart = new MockPart("image", "name", imageBytes);

        //valid json
        byte[] json = "{\"name\":\"Aladdin\",\"age\": 22,\"weight\": 55.6, \"history\": \"SOMEHISTORYYYYYYYY\"}".getBytes();
        MockPart jsonPart = new MockPart("characterRequest", "json", json);
        jsonPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ResultActions response = mockMvc.perform(
                MockMvcRequestBuilders.multipart(HttpMethod.PUT, "/characters/character/{id}", 1)
                        .part(filePart)
                        .part(jsonPart)
        );

        response.andExpect(status().isOk());
    }

    @Test
    public void testUpdateCharacterEndpointAndResponseIs404_NOT_FOUND() throws Exception {
        Long nonExistentId = 1L;

        CharacterRequest characterRequest = CharacterRequest.builder().name("Aladdin").age(22).weight(61.3).history("SOMEHISTORYYYYYYYY").build();

        Path path = Paths.get("src/test/resources/image/jafar.jpg");
        byte[] imageBytes = Files.readAllBytes(path);
        MockPart filePart = new MockPart("image", "name", imageBytes);

        //valid json
        byte[] json = "{\"name\":\"Aladdin\",\"age\": 22,\"weight\": 61.3, \"history\": \"SOMEHISTORYYYYYYYY\"}".getBytes();
        MockPart jsonPart = new MockPart("characterRequest", "json", json);
        jsonPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // Make characterService mock throw CharacterNotFoundException
        when(this.characterService.updateCharacter(eq(nonExistentId), eq(characterRequest), any(MultipartFile.class)))
                .thenThrow(new CharacterNotFoundException("Character Not Found"));


        ResultActions response = mockMvc.perform(
                MockMvcRequestBuilders.multipart(HttpMethod.PUT, "/characters/character/{id}", nonExistentId)
                        .part(filePart)
                        .part(jsonPart)
        );

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Character Not Found"));
    }

    @Test
    public void testUpdateCharacterEndpointAndResponseIs400_BAD_REQUEST() throws Exception {
        Path path = Paths.get("src/test/resources/image/jafar.jpg");
        byte[] imageBytes = Files.readAllBytes(path);
        MockPart filePart = new MockPart("image", "name", imageBytes);

        //invalid json / weight
        byte[] json = "{\"name\":\"Aladdin\",\"age\": 22,\"weight\": 0.000001, \"history\": \"SOMEHISTORYYYYYYYY\"}".getBytes();
        MockPart jsonPart = new MockPart("characterRequest", "json", json);
        jsonPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);


        ResultActions response = mockMvc.perform(
                MockMvcRequestBuilders.multipart(HttpMethod.PUT, "/characters/character/{id}", 1L)
                        .part(filePart)
                        .part(jsonPart)
        );

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("weight"))
                .andExpect(jsonPath("$.errors[0].message").value("weight must be greater or equal than 0.1"));
    }


    @Test
    public void testCreateCharacterEndpointAndResponseIs201_CREATED() throws Exception {
        Path path = Paths.get("src/test/resources/image/jafar.jpg");
        byte[] imageBytes = Files.readAllBytes(path);
        MockPart filePart = new MockPart("image", "name", imageBytes);

        //valid json
        byte[] json = "{\"name\":\"Aladdin\",\"age\": 22,\"weight\": 56.3, \"history\": \"SOMEHISTORYYYYYYYY\"}".getBytes();
        MockPart jsonPart = new MockPart("characterRequest", "json", json);
        jsonPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ResultActions response = mockMvc.perform(
                MockMvcRequestBuilders.multipart("/characters/character")
                        .part(filePart)
                        .part(jsonPart)
        );

        response.andExpect(status().isCreated());
    }

    @Test
    public void testCreateCharacterEndpointAndResponseIs400_BAD_REQUEST() throws Exception {
        Path path = Paths.get("src/test/resources/image/jafar.jpg");
        byte[] imageBytes = Files.readAllBytes(path);
        MockPart filePart = new MockPart("image", "name", imageBytes);

        //invalid json with invalid weight
        byte[] json = "{\"name\":\"Aladdin\",\"age\": 22,\"weight\": 0.0000001, \"history\": \"SOMEHISTORYYYYYYYY\"}".getBytes();
        MockPart jsonPart = new MockPart("characterRequest", "json", json);
        jsonPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // Perform the request with invalid data
        ResultActions response = mockMvc.perform(
                MockMvcRequestBuilders.multipart("/characters/character")
                        .part(filePart) // Attach the image file
                        .part(jsonPart) // Send characterRequest as a parameter
        );

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("weight"))
                .andExpect(jsonPath("$.errors[0].message").value("weight must be greater or equal than 0.1"));
    }


    @Test
    public void testDeleteCharacterEndpointAndResponseIs204_NO_CONTENT() throws Exception {

        ResultActions response = mockMvc.perform(delete("/characters/character/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteCharacterEndpointAndResponseIs404_NOT_FOUND() throws Exception {

        doThrow(CharacterNotFoundException.class).when(this.characterService).deleteCharacter(anyLong());

        ResultActions response = mockMvc.perform(delete("/characters/character/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isNotFound());
    }

    @Test
    public void testSearchCharacterEndpointAndResponseIs200_OK() throws Exception {
        ResultActions response = mockMvc.perform(get("/characters/character/")
                .param("name", "example")
                .param("age", "1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());
    }

    @Test
    public void testSearchCharacterEndpointAndResponseIs404_NOT_FOUND() throws Exception {

        when(this.characterService.searchCharacter(anyString(), anyInt())).thenThrow(CharacterSearchEmptyResultException.class);

        ResultActions response = mockMvc.perform(get("/characters/character")
                .param("name", "something")
                .param("age", "1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isNotFound());
    }
}

