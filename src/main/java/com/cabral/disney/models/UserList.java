package com.cabral.disney.models;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"user", "moviesInList"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UserList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Boolean isPublic;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "user_list_movies",
            joinColumns = @JoinColumn(name = "user_list_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private Set<Movie> moviesInList = new HashSet<>();

}
