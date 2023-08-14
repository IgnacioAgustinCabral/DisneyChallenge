package com.cabral.disney.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Personaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Integer edad;

    private Double peso;

    @Column(columnDefinition = "TEXT")
    private String historia;

    private String imagen;

    @ManyToMany
    @JoinTable(
            name = "pelicula_personaje",
            joinColumns = @JoinColumn(name = "personaje_id"),
            inverseJoinColumns = @JoinColumn(name = "pelicula_id")
    )
    private Set<Pelicula> peliculas_asociadas;
}
