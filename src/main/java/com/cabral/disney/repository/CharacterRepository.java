package com.cabral.disney.repository;

import com.cabral.disney.models.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    @Query("SELECT c FROM Character c WHERE c.name LIKE %:name% AND c.age = :age")
    List<Character> searchCharacter(@Param("name") String name, @Param("age") Integer age);
}
