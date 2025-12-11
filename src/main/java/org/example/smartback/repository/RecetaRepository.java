package org.example.smartback.repository;

import org.example.smartback.model.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Integer> {
    List<Receta> findByUsuarioId(int usuarioId);
    List<Receta> findByCategoriaNombre(String nombre);
    List<Receta> findByIngredientesNombre(String nombre);
    List<Receta> findByPreferenciasNombre(String nombre);


}
