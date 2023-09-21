package com.cabral.disney.service;

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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
}
