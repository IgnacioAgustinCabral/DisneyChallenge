package com.cabral.disney.repository;

import com.cabral.disney.entity.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonajeRepository extends JpaRepository<Personaje,Long> {
}
