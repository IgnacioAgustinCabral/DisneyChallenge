package com.cabral.disney.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    private LocalDate fecha_creacion;
    private Integer calificacion;
    private String imagen;

    @ManyToMany(mappedBy = "peliculas_asociadas")
    private Set<Personaje> personajes_asociados;

    @ManyToMany(mappedBy = "peliculas_asociadas")
    private Set<Genero> generos_asociados;

}