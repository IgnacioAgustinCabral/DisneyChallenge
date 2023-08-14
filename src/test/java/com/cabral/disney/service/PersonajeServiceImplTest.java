package com.cabral.disney.service;

import com.cabral.disney.dto.PersonajeDTO;
import com.cabral.disney.entity.Personaje;
import com.cabral.disney.exception.PersonajeNotFoundException;
import com.cabral.disney.repository.PersonajeRepository;
import com.cabral.disney.service.impl.PersonajeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonajeServiceImplTest {
    @Mock
    private PersonajeRepository personajeRepository;

    @InjectMocks
    private PersonajeServiceImpl personajeService;

    @Test
    public void shouldReturnAllPersonajes(){
        when(this.personajeRepository.findAll()).thenReturn(Arrays.asList(mock(Personaje.class)));

        List<Personaje> personajes = this.personajeService.getAllPersonajes();

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
}
