package org.example.smartback.repository;

import org.example.smartback.model.Preferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferenciaRepository extends JpaRepository<Preferencia, Integer> {
}
