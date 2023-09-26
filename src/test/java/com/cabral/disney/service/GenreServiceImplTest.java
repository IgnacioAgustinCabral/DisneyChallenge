package com.cabral.disney.service;

import com.cabral.disney.exception.GenreNotFoundException;
import com.cabral.disney.models.Genre;
import com.cabral.disney.payload.request.GenreRequest;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
    public void shouldThrowExceptionWhenFindingByANonExistentId() throws GenreNotFoundException {
        when(this.genreRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(GenreNotFoundException.class, () -> {
            this.genreService.getGenreById(anyLong());
        });
    }

    @Test
    public void shouldCreateAGenreReturnsGenreResponse() {
        GenreRequest genreRequest = GenreRequest.builder().name("Comedy").build();

        when(this.genreRepository.save(any(Genre.class))).thenReturn(Genre.builder().id(1L).name("Comedy").build());

        GenreResponse genreResponse = this.genreService.createGenre(genreRequest);

        assertThat(genreResponse).isInstanceOf(GenreResponse.class);
        assertThat(genreResponse.getName()).isEqualTo("Comedy");
        assertThat(genreResponse.getId()).isEqualTo(1L);
    }

    @Test
    public void shouldUpdateAGenre() throws GenreNotFoundException {
//        GenreRequest genreRequest = GenreRequest.builder().name("Comedy").build();

        when(this.genreRepository.save(any(Genre.class)))
                .thenReturn(Genre.builder().id(1L).name("Comedy").build());

        GenreResponse genreResponse = this.genreService.createGenre(mock(GenreRequest.class));

        GenreRequest genreUpdateRequest = GenreRequest.builder().name("Horror").build();

        when(this.genreRepository.findById(genreResponse.getId()))
                .thenReturn(Optional.ofNullable(Genre.builder().id(genreResponse.getId()).name("Horror").build()));

        //Another approach
        /*when(this.genreRepository.save(argThat(updatedGenre -> "Horror".equals(updatedGenre.getName()))))
                .thenReturn(Genre.builder().id(genreResponse.getId()).name("Horror").build());*/
        when(this.genreRepository.save(any(Genre.class)))
                .thenReturn(Genre.builder().id(genreResponse.getId()).name("Horror").build());


        GenreResponse updateGenreResponse = this.genreService.updateGenre(genreResponse.getId(), genreUpdateRequest);

        assertThat(updateGenreResponse.getName()).isEqualTo("Horror");
        assertThat(updateGenreResponse.getId()).isEqualTo(genreResponse.getId());
    }

    @Test
    public void shouldDeleteAGenreById() throws GenreNotFoundException {
        Genre genreMock = mock(Genre.class);
        when(this.genreRepository.findById(anyLong())).thenReturn(Optional.ofNullable(genreMock));

        this.genreService.deleteGenre(anyLong());

        verify(this.genreRepository).delete(genreMock);
    }

    @Test
    public void shouldThrowExceptionWhenTryingToDeleteByNonExistentId() throws GenreNotFoundException {

        when(this.genreRepository.findById(anyLong())).thenReturn(Optional.empty());


        assertThrows(GenreNotFoundException.class, () -> {
            this.genreService.deleteGenre(anyLong());
        });
    }
}
