package com.cabral.disney.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
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

    @ManyToMany(mappedBy = "peliculas_asociadas", fetch = FetchType.EAGER)
    private Set<Personaje> personajes_asociados = new HashSet<>();

    @ManyToMany(mappedBy = "associated_movies", fetch = FetchType.EAGER)
    private Set<Genero> associated_movies = new HashSet<>();

}