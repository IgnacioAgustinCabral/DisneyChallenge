package com.cabral.disney.models;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Personaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Integer edad;

    @Column(nullable = false)
    private Double peso;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String historia;

    private String imagen;

    @ManyToMany(mappedBy = "personajes_asociados", fetch = FetchType.EAGER)
    private Set<Movie> peliculas_asociadas = new HashSet<>();
}


