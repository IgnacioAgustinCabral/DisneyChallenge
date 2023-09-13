package com.cabral.disney.service;

import com.cabral.disney.models.Personaje;
import com.cabral.disney.exception.PersonajeNotFoundException;
import com.cabral.disney.exception.PersonajeSearchEmptyResultException;
import com.cabral.disney.payload.request.PersonajeRequest;
import com.cabral.disney.payload.response.PersonajeResponse;
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

        List<PersonajeResponse> personajes = this.personajeService.getAllPersonajes();

        assertThat(personajes).isNotNull();
    }

    @Test
    public void shouldCreateAPersonajeAndReturnPersonajeResponse(){
        Personaje personaje = Personaje.builder().imagen("images/characters/aladin.jpg").nombre("Aladin").peso(34.4).edad(25).historia("TEXTOTEXTOTEXTOTEXTOOOO").build();
        PersonajeRequest personajeRequest = PersonajeRequest.builder().nombre("Aladdin").edad(22).peso(61.3).historia("HISTORIAXHISTORIAXHISTORIAXXXXX").build();

        when(this.personajeRepository.save(Mockito.any(Personaje.class))).thenReturn(personaje);
        PersonajeResponse savedPersonaje = this.personajeService.createPersonaje(personajeRequest);
        assertThat(savedPersonaje).isNotNull();
        assertThat(savedPersonaje).isInstanceOf(PersonajeResponse.class);
    }

    @Test
    public void shouldGetAPersonajeByIdAndReturnPersonajeResponse() throws PersonajeNotFoundException {
        when(this.personajeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mock(Personaje.class)));
        PersonajeResponse retrievedPersonaje = this.personajeService.getPersonajeById(1L);
        assertThat(retrievedPersonaje).isNotNull();
        assertThat(retrievedPersonaje).isInstanceOf(PersonajeResponse.class);
    }

    @Test
    public void shouldUpdateAPersonajeAndReturnPersonajeResponse() throws PersonajeNotFoundException {

        when(this.personajeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mock(Personaje.class)));
        when(this.personajeRepository.save(any(Personaje.class))).thenReturn(mock(Personaje.class));

        PersonajeResponse personaje = this.personajeService.updatePersonaje(1L,mock(PersonajeRequest.class));

        assertThat(personaje).isNotNull();
        assertThat(personaje).isInstanceOf(PersonajeResponse.class);

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
    public void shouldSearchAPersonajeByNameAndReturnAListOfPersonajeResponseNotEmpty() throws PersonajeSearchEmptyResultException {

        when(this.personajeRepository.searchPersonaje(anyString(), anyInt())).thenReturn(Arrays.asList(mock(Personaje.class)));

        List<PersonajeResponse> personajes = this.personajeService.searchPersonaje(anyString(), anyInt());

        assertThat(personajes).isNotEmpty();
    }

    @Test
    public void shouldSearchAPersonajeByNameAndThrowPersonajeSearchResultEmptyExceptionWhenNothingWasFound(){
        when(this.personajeRepository.searchPersonaje(anyString(), anyInt())).thenReturn(Collections.emptyList());

        assertThrows(PersonajeSearchEmptyResultException.class, () -> {
            this.personajeService.searchPersonaje(anyString(), anyInt());
        });
    }
}
