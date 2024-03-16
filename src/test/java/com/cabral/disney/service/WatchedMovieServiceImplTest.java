package com.cabral.disney.service;

import com.cabral.disney.exception.MovieNotFoundException;
import com.cabral.disney.models.Movie;
import com.cabral.disney.models.User;
import com.cabral.disney.models.WatchedMovie;
import com.cabral.disney.payload.response.WatchedMovieResponse;
import com.cabral.disney.repository.MovieRepository;
import com.cabral.disney.repository.UserRepository;
import com.cabral.disney.repository.WatchedMoviesRepository;
import com.cabral.disney.service.impl.WatchedMoviesServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WatchedMovieServiceImplTest {

    @Mock
    private WatchedMoviesRepository watchedMoviesRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private WatchedMoviesServiceImpl watchedMoviesService;

    @Test
    public void markingAMovieAsWatchedReturnsWatchedMovieResponseDTO() throws MovieNotFoundException {
        User user = mock(User.class);

        Movie movie = mock(Movie.class);

        when(this.movieRepository.findById(anyLong())).thenReturn(Optional.of(movie));
        when(this.userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        when(this.watchedMoviesRepository.save(any(WatchedMovie.class))).thenAnswer(invocation -> invocation.getArgument(0));
        WatchedMovieResponse responseDTO = this.watchedMoviesService.addToWatchedMovies("username", 1L);

        assertThat(responseDTO).isInstanceOf(WatchedMovieResponse.class);
        assertThat(responseDTO.getMovie()).isEqualTo(movie);
        assertThat(responseDTO.getWatchedStatus()).isTrue();
    }

    @Test
    public void unmarkingAMovieAsWatchedReturnsWatchedMovieResponseDTO() throws MovieNotFoundException {
        User user = mock(User.class);

        Movie movie = mock(Movie.class);

        WatchedMovie watchedMovie = WatchedMovie.builder().movie(movie).user(user).build();

        when(this.movieRepository.findById(anyLong())).thenReturn(Optional.of(movie));
        when(this.userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(this.watchedMoviesRepository.findByMovie_IdAndUser_Id(anyLong(), anyLong())).thenReturn(Optional.of(watchedMovie));

        WatchedMovieResponse watchedMovieResponse = this.watchedMoviesService.removeFromWatchedMovies("some_username", 1L);

        assertThat(watchedMovieResponse).isInstanceOf(WatchedMovieResponse.class);
        assertThat(watchedMovieResponse.getMovie()).isEqualTo(movie);
        assertThat(watchedMovieResponse.getWatchedStatus()).isFalse();
        verify(this.watchedMoviesRepository).delete(watchedMovie);
    }
}
