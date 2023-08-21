package com.cabral.disney.service;

import com.cabral.disney.dto.PersonajeDTO;
import com.cabral.disney.entity.Personaje;
import com.cabral.disney.exception.PersonajeNotFoundException;
import com.cabral.disney.exception.PersonajeSearchResultEmptyException;
import com.cabral.disney.repository.PersonajeRepository;
import com.cabral.disney.service.impl.PersonajeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonajeServiceImplTest {
    @Mock
    private PersonajeRepository personajeRepository;

    @InjectMocks
    private PersonajeServiceImpl personajeService;

    @Test
    public void shouldReturnAllPersonajes(){
        when(this.personajeRepository.findAll()).thenReturn(Arrays.asList(mock(Personaje.class)));

        List<PersonajeDTO> personajes = this.personajeService.getAllPersonajes();

        assertThat(personajes).isNotNull();
    }

    @Test
    public void shouldCreateAPersonajeAndReturnPersonajeDTO(){
        Personaje personaje = Personaje.builder().imagen("images/characters/aladin.jpg").nombre("Aladin").peso(34.4).edad(25).historia("TEXTOTEXTOTEXTOTEXTOOOO").build();
        PersonajeDTO personajeDTO = PersonajeDTO.builder().imagen("images/characters/aladin.jpg").nombre("Aladin").peso(34.4).edad(25).historia("TEXTOTEXTOTEXTOTEXTOOOO").build();

        when(this.personajeRepository.save(Mockito.any(Personaje.class))).thenReturn(personaje);
        PersonajeDTO savedPersonaje = this.personajeService.createPersonaje(personajeDTO);
        assertThat(savedPersonaje).isNotNull();
        assertThat(savedPersonaje).isInstanceOf(PersonajeDTO.class);
    }

    @Test
    public void shouldGetAPersonajeByIdAndReturnPersonajeDTO() throws PersonajeNotFoundException {
        when(this.personajeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mock(Personaje.class)));
        PersonajeDTO retrievedPersonaje = this.personajeService.getPersonajeById(1L);
        assertThat(retrievedPersonaje).isNotNull();
        assertThat(retrievedPersonaje).isInstanceOf(PersonajeDTO.class);
    }

    @Test
    public void shouldUpdateAPersonajeAndReturnPersonajeDTO() throws PersonajeNotFoundException {

        when(this.personajeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mock(Personaje.class)));
        when(this.personajeRepository.save(any(Personaje.class))).thenReturn(mock(Personaje.class));

        PersonajeDTO personajeDTO = this.personajeService.updatePersonaje(1L,mock(PersonajeDTO.class));

        assertThat(personajeDTO).isNotNull();
        assertThat(personajeDTO).isInstanceOf(PersonajeDTO.class);

    }

    @Test
    public void shouldDeleteAPersonaje() throws PersonajeNotFoundException {

        Personaje mockPersonaje = Mockito.mock(Personaje.class);

        when(this.personajeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mockPersonaje));

        this.personajeService.deletePersonaje(1L);

        //verify that delete was called
        verify(this.personajeRepository).delete(mockPersonaje);
    }

    @Test
    public void shouldSearchAPersonajeByNameAndReturnAListOfPersonajeDTONotEmpty() throws PersonajeSearchResultEmptyException {

        when(this.personajeRepository.searchPersonaje(anyString())).thenReturn(Arrays.asList(mock(Personaje.class)));

        List<PersonajeDTO> personajesDTOs = this.personajeService.searchPersonaje(anyString());

        assertThat(personajesDTOs).isNotEmpty();
    }

    @Test
    public void shouldSearchAPersonajeByNameAndThrowPersonajeSearchResultEmptyExceptionWhenNothingWasFound(){
        when(this.personajeRepository.searchPersonaje(anyString())).thenReturn(Collections.emptyList());

        assertThrows(PersonajeSearchResultEmptyException.class, () -> {
            this.personajeService.searchPersonaje(anyString());
        });
    }
}
