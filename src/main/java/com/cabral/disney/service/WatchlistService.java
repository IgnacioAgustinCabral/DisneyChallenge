package com.cabral.disney.service;

import com.cabral.disney.payload.response.WatchlistResponse;

import java.util.List;

public interface WatchlistService {
    List<WatchlistResponse> getAllMoviesInWatchlist(Long userId);
}
