package com.cabral.disney.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private LocalDate fecha_creacion;
    private Integer calificacion;
    private String imagen;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "movie_personaje",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "personaje_id")
    )
    private Set<Personaje> personajes_asociados = new HashSet<>();

    @ManyToMany(mappedBy = "associated_movies", fetch = FetchType.LAZY)
    private Set<Genero> associated_generes = new HashSet<>();

}