package org.example.smartback.repository;

import org.example.smartback.model.MeGusta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeGustaRepository extends JpaRepository<MeGusta, Integer> {

    List<MeGusta> findByUsuarioId(int usuarioId);

    List<MeGusta> findByRecetaId(int recetaId);

    MeGusta findByUsuarioIdAndRecetaId(int usuarioId, int recetaId);
}
