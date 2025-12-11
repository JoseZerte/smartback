package org.example.smartback.repository;

import org.example.smartback.model.MeGusta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeGustaRepository extends JpaRepository<MeGusta, Integer> {

    // Lista todos los "me gusta" de un usuario
    List<MeGusta> findByUsuarioId(int usuarioId);

    // Lista todos los "me gusta" de una receta
    List<MeGusta> findByRecetaId(int recetaId);

    // Buscar si un usuario ya marcó una receta como favorita
    MeGusta findByUsuarioIdAndRecetaId(int usuarioId, int recetaId);
}
