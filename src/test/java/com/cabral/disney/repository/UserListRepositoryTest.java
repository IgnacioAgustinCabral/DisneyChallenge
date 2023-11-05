package com.cabral.disney.repository;

import com.cabral.disney.models.Movie;
import com.cabral.disney.models.User;
import com.cabral.disney.models.UserList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserListRepositoryTest {

    @Autowired
    private UserListRepository userListRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private EntityManager entityManager;
    private User user;
    private Movie movie1;
    private Movie movie2;

    private Set<Movie> movies = new HashSet<>();

    @BeforeEach
    public void init() {
        byte[] image = new byte[]{0x12, 0x34, 0x56, 0x78};

        user = User.builder().email("asd@gmail.com").password("123123").profile_picture(image).username("Kippur").build();
        movie1 = Movie.builder().title("Aladin").synopsis("synopsis").creationDate(LocalDate.of(1994, 2, 2)).build();
        movie2 = Movie.builder().title("LionKing").synopsis("synopsis").creationDate(LocalDate.of(1994, 2, 2)).build();

        movies.add(movie1);
        movies.add(movie2);
    }

    @Test
    public void aUserCanCreateAList() {
        User savedUser = this.userRepository.save(this.user);
        UserList userList = UserList.builder().name("Action Movies").isPublic(true).moviesInList(Set.of(movie1, movie2)).user(savedUser).build();

        UserList savedUserList = this.userListRepository.save(userList);

        assertThat(savedUserList).isNotNull();

        assertThat(savedUserList.getMoviesInList().size()).isEqualTo(2);

        assertThat(savedUserList.getIsPublic()).isEqualTo(true);

        assertThat(savedUserList.getUser().getId()).isEqualTo(savedUser.getId());

        assertThat(savedUserList.getName()).isEqualTo("Action Movies");
    }

    @Test
    public void aUserCanHaveMultipleLists() {
        this.movieRepository.save(movie1);
        this.movieRepository.save(movie2);

        User savedUser = this.userRepository.save(this.user);

        UserList userList1 = UserList.builder().name("Action Movies").isPublic(true).moviesInList(Set.of(movie1, movie2)).user(savedUser).build();

        UserList userList2 = UserList.builder().name("Romance Movies").isPublic(true).moviesInList(Set.of(movie1, movie2)).user(savedUser).build();

        this.userListRepository.save(userList1);
        this.userListRepository.save(userList2);

        // refreshes the user, changes may not be always be in sync with the db
        entityManager.refresh(savedUser);

        User retrievedUser = this.userRepository.findByUsername(savedUser.getUsername()).get();

        assertThat(retrievedUser.getMovieLists().size()).isEqualTo(2);
    }

    @Test
    public void aUserCanRemoveMoviesFromAList() {
        UserList userList = UserList.builder().name("Action Movies").isPublic(true).moviesInList(movies).user(user).build();

        this.userListRepository.save(userList);

        userList.getMoviesInList().remove(movie1);

        UserList updatedList = this.userListRepository.findById(userList.getId()).orElse(null);

        assertThat(updatedList.getMoviesInList().size()).isEqualTo(1);
    }

}
