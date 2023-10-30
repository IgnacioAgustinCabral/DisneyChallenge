package com.cabral.disney.service.impl;

import com.cabral.disney.exception.MovieNotFoundException;
import com.cabral.disney.models.Movie;
import com.cabral.disney.models.User;
import com.cabral.disney.repository.MovieRepository;
import com.cabral.disney.repository.UserRepository;
import com.cabral.disney.service.LikeMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LikeMovieServiceImpl implements LikeMovieService {

    private UserRepository userRepository;
    private MovieRepository movieRepository;

    @Autowired
    public LikeMovieServiceImpl(UserRepository userRepository, MovieRepository movieRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public String likeMovie(Long movieId, Long userId) throws MovieNotFoundException {
        Movie movie = this.movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found"));

        User user = this.userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.getLikedMovies().add(movie);

        this.userRepository.save(user);

        return movie.getTitle();
    }

    @Override
    public String removeLike(Long movieId, Long userId) throws MovieNotFoundException {
        Movie movie = this.movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found"));

        User user = this.userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.getLikedMovies().remove(movie);

        this.userRepository.save(user);

        return movie.getTitle();
    }
}
