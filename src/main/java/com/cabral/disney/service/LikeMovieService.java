package com.cabral.disney.service;

import com.cabral.disney.exception.MovieNotFoundException;
import com.cabral.disney.payload.response.MovieResponse;

import java.util.List;

public interface LikeMovieService {

    String likeMovie(Long movieId, Long userId) throws MovieNotFoundException;

    String removeLike(Long movieId, Long userId) throws MovieNotFoundException;

    List<MovieResponse> getMoviesLikedByUser(String username);
}
