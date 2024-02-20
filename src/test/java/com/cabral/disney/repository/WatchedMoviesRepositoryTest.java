package com.cabral.disney.repository;

import com.cabral.disney.models.Movie;
import com.cabral.disney.models.User;
import com.cabral.disney.models.WatchedMovie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WatchedMoviesRepositoryTest {

    @Autowired
    private WatchedMoviesRepository watchedMoviesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private EntityManager entityManager;

    private User user;
    private Movie movie1;
    private Movie movie2;

    private WatchedMovie watchedMovie1;
    private WatchedMovie watchedMovie2;


    @BeforeEach
    public void init() {
        byte[] image = new byte[]{0x12, 0x34, 0x56, 0x78};

        user = User.builder().email("asd@gmail.com").password("123123").profile_picture(image).username("Kippur").build();
        this.userRepository.save(user);
        entityManager.refresh(user);

        movie1 = Movie.builder().title("Aladdin").synopsis("synopsis").creationDate(LocalDate.of(1994, 2, 2)).build();
        movie2 = Movie.builder().title("LionKing").synopsis("synopsis").creationDate(LocalDate.of(1994, 2, 2)).build();
        this.movieRepository.save(movie1);
        this.movieRepository.save(movie2);

        watchedMovie1 = WatchedMovie.builder().movie(movie1).user(user).seenAt(LocalDateTime.now()).build();
        watchedMovie2 = WatchedMovie.builder().movie(movie2).user(user).seenAt(LocalDateTime.now()).build();
        this.watchedMoviesRepository.save(watchedMovie1);
        this.watchedMoviesRepository.save(watchedMovie2);
    }

    @Test
    public void aUserCanMarkMoviesAsWatched() {
        entityManager.flush();

        User retrievedUser = this.userRepository.findByUsername(user.getUsername()).get();

        assertThat(retrievedUser.getWatchedMovies().size()).isEqualTo(2);
    }

}
