package com.cabral.disney.controller;

import com.cabral.disney.exception.MovieNotFoundException;
import com.cabral.disney.models.User;
import com.cabral.disney.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/watchlist")
public class WatchlistController {

    private WatchlistService watchlistService;

    @Autowired
    public WatchlistController(WatchlistService watchlistService) {
        this.watchlistService = watchlistService;
    }

    @PostMapping("/{movieId}/add-to-watchlist")
    public ResponseEntity<?> addToWatchlist(@PathVariable Long movieId, @AuthenticationPrincipal UserDetails user) throws MovieNotFoundException {
        String movieTitle = this.watchlistService.addMovieToWatchlist(movieId, user.getUsername());
        Map<String, String> response = new HashMap<>();

        response.put("message", movieTitle + " was added to your watchlist");

        return ResponseEntity.ok(response);
    }

}
