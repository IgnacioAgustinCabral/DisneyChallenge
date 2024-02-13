package com.cabral.disney.controller;

import com.cabral.disney.exception.MovieNotFoundException;
import com.cabral.disney.models.User;
import com.cabral.disney.service.WatchlistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/watchlist")
@Slf4j
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

    @DeleteMapping("/{movieId}/remove")
    public ResponseEntity<?> removeFromWatchlist(@PathVariable Long movieId, @AuthenticationPrincipal UserDetails user) throws MovieNotFoundException {
        String movieTitle = this.watchlistService.removeMovieFromWatchlist(movieId, user.getUsername());
        Map<String,String> response = new HashMap<>();
        response.put("message",movieTitle+" was removed from your watchlist.");

        return ResponseEntity.ok(response);
    }

}
