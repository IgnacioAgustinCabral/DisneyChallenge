package com.cabral.disney.mapper;

import com.cabral.disney.models.Watchlist;
import com.cabral.disney.payload.response.MovieResponse;
import com.cabral.disney.payload.response.WatchlistResponse;
import org.springframework.stereotype.Component;

@Component
public class WatchlistMapper {
    public static WatchlistResponse mapToDTO(Watchlist watchlist) {
        MovieResponse movieResponse = MovieMapper.mapToDTO(watchlist.getMovie());

        return WatchlistResponse.builder()
                .movie(movieResponse)
                .addedAt(watchlist.getAddedAt())
                .build();
    }
}
