package com.cabral.disney.controller;

import com.cabral.disney.exception.ListCreationValidationException;
import com.cabral.disney.exception.MovieNotFoundException;
import com.cabral.disney.models.User;
import com.cabral.disney.payload.request.ListRequest;
import com.cabral.disney.payload.response.MovieResponse;
import com.cabral.disney.payload.response.ListResponse;
import com.cabral.disney.service.LikeMovieService;
import com.cabral.disney.service.UserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private LikeMovieService likeMovieService;
    private UserListService userListService;

    @Autowired
    public UserController(LikeMovieService likeMovieService, UserListService userListService) {
        this.likeMovieService = likeMovieService;
        this.userListService = userListService;
    }

    @PostMapping("/like-movie/{movieId}")
    public ResponseEntity<Map<String, String>> likeMovie(@PathVariable Long movieId, @AuthenticationPrincipal User userDetails) throws MovieNotFoundException {
        String movieTitle = this.likeMovieService.likeMovie(movieId, userDetails.getId());

        Map<String, String> response = new HashMap<>();

        response.put("message", "You've liked " + movieTitle);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/remove-like/{movieId}")
    public ResponseEntity<?> removeLike(@PathVariable Long movieId, @AuthenticationPrincipal User userDetails) throws MovieNotFoundException {
        String movieTitle = this.likeMovieService.removeLike(movieId, userDetails.getId());

        Map<String, String> response = new HashMap<>();

        response.put("message", "You've removed " + movieTitle + " from your liked movies");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/likes/{username}")
    public ResponseEntity<?> listLikedMovies(@PathVariable String username) {
        List<MovieResponse> likedMovies = this.likeMovieService.getMoviesLikedByUser(username);

        return ResponseEntity.ok(likedMovies);
    }

    @PostMapping("/new-list")
    public ResponseEntity<?> createList(@Valid @RequestBody ListRequest listRequest, @AuthenticationPrincipal User user) throws ListCreationValidationException {
        ListResponse listResponse = this.userListService.createList(listRequest, user);

        return ResponseEntity.ok(listResponse);
    }
}
