package com.cabral.disney.service;

import com.cabral.disney.exception.EmptyWatchlistException;
import com.cabral.disney.exception.MovieNotFoundException;
import com.cabral.disney.payload.response.WatchlistResponse;

import java.util.List;

public interface WatchlistService {
    List<WatchlistResponse> getAllMoviesInWatchlist(Long userId) throws EmptyWatchlistException;

    String removeMovieFromWatchlist(Long movieId, String userId) throws MovieNotFoundException;

    String addMovieToWatchlist(Long movieId, String username) throws MovieNotFoundException;
}
