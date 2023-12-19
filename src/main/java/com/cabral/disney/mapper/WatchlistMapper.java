package com.cabral.disney.mapper;

import com.cabral.disney.models.Movie;
import com.cabral.disney.models.User;
import com.cabral.disney.models.Watchlist;
import com.cabral.disney.payload.response.MovieResponse;
import com.cabral.disney.payload.response.WatchlistResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class WatchlistMapper {
    public static WatchlistResponse mapToDTO(Watchlist watchlist) {
        MovieResponse movieResponse = MovieMapper.mapToDTO(watchlist.getMovie());

        return WatchlistResponse.builder()
                .movie(movieResponse)
                .addedAt(watchlist.getAddedAt())
                .build();
    }

    public static Watchlist mapToEntity(Movie movie, User user) {
        return Watchlist.builder()
                .user(user)
                .movie(movie)
                .addedAt(LocalDateTime.now())
                .build();
    }
}
