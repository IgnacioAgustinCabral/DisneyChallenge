package com.cabral.disney.service;

import com.cabral.disney.exception.GenreNotFoundException;
import com.cabral.disney.models.Genre;
import com.cabral.disney.payload.response.GenreResponse;
import com.cabral.disney.repository.GenreRepository;
import com.cabral.disney.service.impl.GenreServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GenreServiceImplTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreServiceImpl genreService;

    @Test
    public void shouldReturnAListOfGenreResponses() {
        when(this.genreRepository.findAll()).thenReturn(Arrays.asList(mock(Genre.class), mock(Genre.class)));

        List<GenreResponse> genres = this.genreService.getAllGenres();

        assertThat(genres.size()).isEqualTo(2);
    }

    @Test
    public void shouldFindGenreByIdAndReturnAGenreResponse() throws GenreNotFoundException {
        when(this.genreRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mock(Genre.class)));

        assertDoesNotThrow(() -> {
            this.genreService.getGenreById(anyLong());
        });
    }

    @Test
    public void shouldThrowExceptionWhenFindingByANonExistentId() throws GenreNotFoundException{
        when(this.genreRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(GenreNotFoundException.class,() -> {
            this.genreService.getGenreById(anyLong());
        });
    }
}
