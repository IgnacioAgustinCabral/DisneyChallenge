package com.cabral.disney.service;

import com.cabral.disney.dto.PeliculaDTO;
import com.cabral.disney.entity.Pelicula;
import com.cabral.disney.exception.PeliculaNotFoundException;
import com.cabral.disney.repository.PeliculaRepository;
import com.cabral.disney.service.impl.PeliculaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PeliculaServiceImplTest {

    @Mock
    private PeliculaRepository peliculaRepository;
    @InjectMocks
    private PeliculaServiceImpl peliculaService;

    @Test
    public void shouldGetAllPeliculasAndReturnListOfPeliculaDTO(){
        when(this.peliculaRepository.findAll()).thenReturn(Arrays.asList(mock(Pelicula.class)));
        List<PeliculaDTO> peliculas = this.peliculaService.getAllPeliculas();

        assertThat(peliculas).isNotEmpty();
    }

    @Test
    public void shouldGetAPeliculaByIdAndReturnPeliculaDTO() throws PeliculaNotFoundException {

        when(this.peliculaRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mock(Pelicula.class)));

        PeliculaDTO peliculaDTO = this.peliculaService.getPeliculaById(anyLong());

        assertThat(peliculaDTO).isInstanceOf(PeliculaDTO.class);
    }
}
