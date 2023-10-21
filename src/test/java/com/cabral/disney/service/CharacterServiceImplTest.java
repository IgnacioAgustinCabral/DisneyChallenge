package com.cabral.disney.service;

import com.cabral.disney.exception.CharacterNotFoundException;
import com.cabral.disney.exception.CharacterSearchEmptyResultException;
import com.cabral.disney.models.Character;
import com.cabral.disney.payload.request.CharacterRequest;
import com.cabral.disney.payload.response.CharacterResponse;
import com.cabral.disney.repository.CharacterRepository;
import com.cabral.disney.service.impl.CharacterServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CharacterServiceImplTest {
    @Mock
    private CharacterRepository characterRepository;

    @InjectMocks
    private CharacterServiceImpl characterService;

    @Test
    public void shouldReturnAllCharacters() {
        when(this.characterRepository.findAll()).thenReturn(Arrays.asList(mock(Character.class)));

        List<CharacterResponse> characters = this.characterService.getAllCharacters();

        assertThat(characters).isNotNull();
    }

    @Test
    public void shouldCreateACharacterAndReturnCharacterResponse() throws IOException {
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getBytes()).thenReturn(new byte[]{0x12, 0x34, 0x56, 0x78});

        when(this.characterRepository.save(any(Character.class))).thenReturn(mock(Character.class));

        CharacterResponse savedCharacter = this.characterService.createCharacter(mock(CharacterRequest.class), mockFile);

        assertThat(savedCharacter).isNotNull();
        assertThat(savedCharacter).isInstanceOf(CharacterResponse.class);
    }

    @Test
    public void shouldGetACharacterByIdAndReturnCharacterResponse() throws CharacterNotFoundException {
        when(this.characterRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mock(Character.class)));
        CharacterResponse retrievedCharacter = this.characterService.getCharacterById(1L);
        assertThat(retrievedCharacter).isNotNull();
        assertThat(retrievedCharacter).isInstanceOf(CharacterResponse.class);
    }

    @Test
    public void shouldUpdateACharacterAndReturnCharacterResponse() throws CharacterNotFoundException, IOException {

        when(this.characterRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mock(Character.class)));
        when(this.characterRepository.save(any(Character.class))).thenReturn(mock(Character.class));

        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getBytes()).thenReturn(new byte[]{0x12, 0x34, 0x56, 0x78});

        CharacterResponse character = this.characterService.updateCharacter(1L, mock(CharacterRequest.class), mockFile);

        assertThat(character).isNotNull();
        assertThat(character).isInstanceOf(CharacterResponse.class);
    }

    @Test
    public void shouldDeleteACharacter() throws CharacterNotFoundException {

        Character mockCharacter = Mockito.mock(Character.class);

        when(this.characterRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mockCharacter));

        this.characterService.deleteCharacter(1L);

        //verify that delete was called
        verify(this.characterRepository).delete(mockCharacter);
    }

    @Test
    public void shouldSearchACharacterByNameAndReturnAListOfCharacterResponseNotEmpty() throws CharacterSearchEmptyResultException {

        when(this.characterRepository.searchCharacter(anyString(), anyInt())).thenReturn(Arrays.asList(mock(Character.class)));

        List<CharacterResponse> characters = this.characterService.searchCharacter(anyString(), anyInt());

        assertThat(characters).isNotEmpty();
    }

    @Test
    public void shouldSearchACharacterByNameAndThrowCharacterSearchResultEmptyExceptionWhenNothingWasFound() {
        when(this.characterRepository.searchCharacter(anyString(), anyInt())).thenReturn(Collections.emptyList());

        assertThrows(CharacterSearchEmptyResultException.class, () -> {
            this.characterService.searchCharacter(anyString(), anyInt());
        });
    }
}
