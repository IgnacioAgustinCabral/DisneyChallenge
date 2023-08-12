package com.cabral.disney.service;

import com.cabral.disney.entity.Personaje;
import com.cabral.disney.repository.PersonajeRepository;
import com.cabral.disney.service.impl.PersonajeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
}
