package com.cabral.disney.service;

import com.cabral.disney.exception.EmptyWatchlistException;
import com.cabral.disney.exception.MovieNotFoundException;
import com.cabral.disney.models.Movie;
import com.cabral.disney.models.Watchlist;
import com.cabral.disney.payload.response.WatchlistResponse;
import com.cabral.disney.repository.WatchlistRepository;
import com.cabral.disney.service.impl.WatchlistServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WatchlistServiceImplTest {

    @Mock
    private WatchlistRepository watchlistRepository;

    @InjectMocks
    private WatchlistServiceImpl watchlistService;

    @Test
    public void getAllMoviesInAUserWatchlistAndReturnWatchlistResponse() throws EmptyWatchlistException {
        Movie movie1 = mock(Movie.class);
        when(movie1.getId()).thenReturn(1L);

        Movie movie2 = mock(Movie.class);
        when(movie2.getId()).thenReturn(2L);

        Watchlist watchlist1 = mock(Watchlist.class);
        when(watchlist1.getMovie()).thenReturn(movie1);

        Watchlist watchlist2 = mock(Watchlist.class);
        when(watchlist2.getMovie()).thenReturn(movie2);

        when(this.watchlistRepository.findAllByUser_Id(anyLong())).thenReturn(Arrays.asList(watchlist1, watchlist2));

        List<WatchlistResponse> watchlistResponses = this.watchlistService.getAllMoviesInWatchlist(1L);

        assertThat(watchlistResponses.size()).isEqualTo(2);
        assertThat(watchlistResponses.get(0)).isInstanceOf(WatchlistResponse.class);
    }

    @Test
    public void givenEmptyWatchlist_whenRetrieveWatchlist_thenThrowsEmptyWatchlistException() {
        when(this.watchlistRepository.findAllByUser_Id(anyLong())).thenReturn(Collections.emptyList());

        assertThrows(EmptyWatchlistException.class, () -> {
            this.watchlistService.getAllMoviesInWatchlist(1L);
        });

    }

    @Test
    public void givenExistingMovieIdAndUserId_whenRemovingMovieFromWatchlist_thenVerifyDeletion() throws MovieNotFoundException {
        Watchlist mock = mock(Watchlist.class);

        when(this.watchlistRepository.findByMovie_IdAndUser_Id(anyLong(), anyLong())).thenReturn(Optional.of(mock));

        this.watchlistService.removeMovieFromWatchlist(1L, 1L);

        verify(this.watchlistRepository).delete(mock);
    }

    @Test
    public void givenNonExistentMovieId_whenRemovingMovieFromWatchlist_thenThrowException() {
        when(this.watchlistRepository.findByMovie_IdAndUser_Id(anyLong(), anyLong())).thenReturn(Optional.empty());


        assertThrows(MovieNotFoundException.class, () -> {
            this.watchlistService.removeMovieFromWatchlist(1L, 1L);
        });

    }

}
