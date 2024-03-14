package com.cabral.disney.mapper;

import com.cabral.disney.models.Movie;
import com.cabral.disney.models.User;
import com.cabral.disney.models.WatchedMovie;
import com.cabral.disney.payload.response.WatchedMovieResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class WatchedMovieMapper {
    public static WatchedMovie mapToEntity(User user, Movie movie) {
        return WatchedMovie.builder()
                .movie(movie)
                .user(user)
                .seenAt(LocalDateTime.now())
                .build();
    }

    public static WatchedMovieResponse mapToDTO(Movie movie, Boolean bool) {
        return WatchedMovieResponse.builder()
                .movie(movie)
                .watchedStatus(bool)
                .build();
    }
}
