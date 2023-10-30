package com.cabral.disney.service;

import com.cabral.disney.exception.MovieNotFoundException;

public interface LikeMovieService {

    String likeMovie(Long movieId, Long userId) throws MovieNotFoundException;
}
