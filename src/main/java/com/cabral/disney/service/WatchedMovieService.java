package com.cabral.disney.service;

import com.cabral.disney.exception.MovieNotFoundException;
import com.cabral.disney.payload.response.WatchedMovieResponse;

public interface WatchedMovieService {
    WatchedMovieResponse addToWatchedMovies(String username, Long movieId) throws MovieNotFoundException;
}
