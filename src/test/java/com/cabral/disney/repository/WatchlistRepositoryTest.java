package com.cabral.disney.repository;

import com.cabral.disney.models.Movie;
import com.cabral.disney.models.User;
import com.cabral.disney.models.Watchlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WatchlistRepositoryTest {

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    private User user;
    private User savedUser;

    private Movie movie1;
    private Movie movie2;
    private Movie savedMovie1;
    private Movie savedMovie2;
    private Watchlist watchlist1;
    private Watchlist watchlist2;

    @BeforeEach
    public void init() {
        user = User.builder().username("Kippur").email("example@example.com").password("123456789").build();
        savedUser = this.userRepository.save(user);

        movie1 = Movie.builder().title("Aladdin").creationDate(LocalDate.of(1994, 01, 02)).synopsis("Synopsis 1").build();
        movie2 = Movie.builder().title("Toy Story").creationDate(LocalDate.of(1995, 01, 01)).synopsis("Synopsis 2").build();

        savedMovie1 = this.movieRepository.save(movie1);
        savedMovie2 = this.movieRepository.save(movie2);

        watchlist1 = Watchlist.builder().user(savedUser).movie(savedMovie1).build();
        watchlist2 = Watchlist.builder().user(savedUser).movie(savedMovie2).build();

        this.watchlistRepository.save(watchlist1);
        this.watchlistRepository.save(watchlist2);
    }

    @Test
    public void givenUser_whenAddingMoviesToWatchlist_thenMoviesAreAddedToUserWatchlist() {
        List<Watchlist> list = this.watchlistRepository.findAllByUser_Id(savedUser.getId());

        assertThat(list.size()).isEqualTo(2);
    }
}
