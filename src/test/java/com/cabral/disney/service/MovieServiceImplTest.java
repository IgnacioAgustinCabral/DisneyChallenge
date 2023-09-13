package com.cabral.disney.service;

import com.cabral.disney.models.Movie;
import com.cabral.disney.exception.MovieNotFoundException;
import com.cabral.disney.exception.MovieSearchEmptyResultException;
import com.cabral.disney.payload.request.MovieRequest;
import com.cabral.disney.payload.response.MovieResponse;
import com.cabral.disney.repository.MovieRepository;
import com.cabral.disney.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;
    @InjectMocks
    private MovieServiceImpl movieService;

    @Test
    public void shouldGetAllMoviesAndReturnListOfMovieResponse() {
        when(this.movieRepository.findAll()).thenReturn(Arrays.asList(mock(Movie.class)));
        List<MovieResponse> movieResponses = this.movieService.getAllMovies();

        assertThat(movieResponses).isNotEmpty();
    }

    @Test
    public void shouldGetAMovieByIdAndReturnMovieResponse() throws MovieNotFoundException {

        when(this.movieRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mock(Movie.class)));

        MovieResponse movieResponse = this.movieService.getMovieById(anyLong());

        assertThat(movieResponse).isInstanceOf(MovieResponse.class);
    }

    @Test
    public void shouldCreateAMovieAndReturnMovieResponse() {

        when(this.movieRepository.save(any(Movie.class))).thenReturn(mock(Movie.class));

        MovieResponse createdMovie = this.movieService.createMovie(mock(MovieRequest.class));

        assertThat(createdMovie).isInstanceOf(MovieResponse.class);
    }

    @Test
    public void shouldUpdateAMovieAndReturnMovieResponse() throws MovieNotFoundException {
        when(this.movieRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mock(Movie.class)));
        when(this.movieRepository.save(any(Movie.class))).thenReturn(mock(Movie.class));

        MovieResponse updatedMovie = this.movieService.updateMovie(1L, mock(MovieRequest.class));

        assertThat(updatedMovie).isInstanceOf(MovieResponse.class);
    }

    @Test
    public void shouldDeleteAMovieById() throws MovieNotFoundException {
        Movie mockMovie = mock(Movie.class);

        when(this.movieRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mockMovie));

        this.movieService.deleteMovie(1L);

        verify(this.movieRepository).delete(mockMovie);
    }

    @Test
    public void shouldSearchAMovieByNombreAndReturnListOfMovieResponse() throws MovieSearchEmptyResultException {
        when(this.movieRepository.searchMovie(anyString())).thenReturn(Arrays.asList(mock(Movie.class)));

        List<MovieResponse> movieResponses = this.movieService.searchMovie(anyString());

        assertThat(movieResponses).isInstanceOf(List.class);

        assertThat(movieResponses.get(0)).isInstanceOf(MovieResponse.class);
    }
}
