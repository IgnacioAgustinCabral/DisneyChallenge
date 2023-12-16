package com.cabral.disney.service;

import com.cabral.disney.models.Movie;
import com.cabral.disney.models.User;
import com.cabral.disney.models.Watchlist;
import com.cabral.disney.payload.response.WatchlistResponse;
import com.cabral.disney.repository.WatchlistRepository;
import com.cabral.disney.service.impl.WatchlistServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WatchlistServiceImplTest {

    @Mock
    private WatchlistRepository watchlistRepository;

    @InjectMocks
    private WatchlistServiceImpl watchlistService;

    private Watchlist watchlist1;
    private Watchlist watchlist2;

    @BeforeEach
    public void init() {
        Movie movie1 = mock(Movie.class);
        when(movie1.getId()).thenReturn(1L);

        Movie movie2 = mock(Movie.class);
        when(movie2.getId()).thenReturn(2L);

        watchlist1 = mock(Watchlist.class);
        when(watchlist1.getMovie()).thenReturn(movie1);

        watchlist2 = mock(Watchlist.class);
        when(watchlist2.getMovie()).thenReturn(movie2);
    }

    @Test
    public void getAllMoviesInAUserWatchlistAndReturnWatchlistResponse() {
        when(this.watchlistRepository.findAllByUser_Id(anyLong())).thenReturn(Arrays.asList(watchlist1, watchlist2));

        List<WatchlistResponse> watchlistResponses = this.watchlistService.getAllMoviesInWatchlist(1L);

        assertThat(watchlistResponses.size()).isEqualTo(2);
        assertThat(watchlistResponses.get(0)).isInstanceOf(WatchlistResponse.class);
    }

}
