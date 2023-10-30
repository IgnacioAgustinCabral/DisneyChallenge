package com.cabral.disney.service.impl;

import com.cabral.disney.exception.MovieNotFoundException;
import com.cabral.disney.models.LikedMovies;
import com.cabral.disney.models.Movie;
import com.cabral.disney.models.User;
import com.cabral.disney.repository.LikedMovieRepository;
import com.cabral.disney.repository.MovieRepository;
import com.cabral.disney.repository.UserRepository;
import com.cabral.disney.service.LikeMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LikeMovieServiceImpl implements LikeMovieService {

    private UserRepository userRepository;
    private MovieRepository movieRepository;
    private LikedMovieRepository likedMovieRepository;

    @Autowired
    public LikeMovieServiceImpl(UserRepository userRepository, MovieRepository movieRepository, LikedMovieRepository likedMovieRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.likedMovieRepository = likedMovieRepository;
    }

    @Override
    public String likeMovie(Long movieId, Long userId) throws MovieNotFoundException {
        Movie movie = this.movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found"));

        User user = this.userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        LikedMovies likedMovies = new LikedMovies();
        likedMovies.setUser(user);
        likedMovies.setMovie(movie);
        likedMovies.setLikedAt(LocalDateTime.now());

        this.likedMovieRepository.save(likedMovies);

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
