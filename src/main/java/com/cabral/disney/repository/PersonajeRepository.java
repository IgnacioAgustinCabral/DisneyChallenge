package com.cabral.disney.repository;

import com.cabral.disney.entity.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonajeRepository extends JpaRepository<Personaje, Long> {
    @Query("SELECT p FROM Personaje p WHERE p.nombre LIKE %:name% AND p.edad = :age")
    List<Personaje> searchPersonaje(@Param("name") String name, @Param("age") Integer age);
}
