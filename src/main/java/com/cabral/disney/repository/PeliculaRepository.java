package com.cabral.disney.repository;

import com.cabral.disney.entity.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
    @Query("SELECT p FROM Pelicula p WHERE p.titulo LIKE %:name%")
    List<Pelicula> searchPelicula(@Param("name") String name);
}
