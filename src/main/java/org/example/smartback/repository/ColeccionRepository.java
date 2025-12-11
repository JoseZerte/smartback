package org.example.smartback.repository;

import org.example.smartback.model.Coleccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColeccionRepository extends JpaRepository<Coleccion, Integer> {
}
