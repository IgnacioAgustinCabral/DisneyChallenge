package com.cabral.disney.repository;

import com.cabral.disney.models.Movie;
import com.cabral.disney.models.User;
import com.cabral.disney.models.UserList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserListRepositoryTest {

    @Autowired
    private UserListRepository userListRepository;

    @Autowired
    private UserRepository userRepository;
    private User user;
    private Movie movie1;
    private Movie movie2;

    private User savedUser;

    @BeforeEach
    public void init() {
        byte[] image = new byte[]{0x12, 0x34, 0x56, 0x78};

        user = User.builder().email("asd@gmail.com").password("123123").profile_picture(image).username("Kippur").build();
        movie1 = Movie.builder().title("Aladin").synopsis("synopsis").creationDate(LocalDate.of(1994, 2, 2)).build();
        movie2 = Movie.builder().title("LionKing").synopsis("synopsis").creationDate(LocalDate.of(1994, 2, 2)).build();

        savedUser = this.userRepository.save(this.user);
    }

    @Test
    public void aUserCanCreateAList() {
        UserList userList = UserList.builder().name("Action Movies").isPublic(true).moviesInList(Set.of(movie1,movie2)).user(this.user).build();

        UserList savedUserList = this.userListRepository.save(userList);

        assertThat(savedUserList).isNotNull();

        assertThat(savedUserList.getMoviesInList().size()).isEqualTo(2);

        assertThat(savedUserList.getIsPublic()).isEqualTo(true);

        assertThat(savedUserList.getUser().getId()).isEqualTo(savedUser.getId());

        assertThat(savedUserList.getName()).isEqualTo("Action Movies");
    }

}
