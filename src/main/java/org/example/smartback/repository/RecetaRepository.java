package org.example.smartback.repository;

import org.example.smartback.model.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Integer> {

    List<Receta> findByUsuarioId(int usuarioId);

    List<Receta> findByCategoriaNombre(String nombre);

    List<Receta> findByPreferencias_Nombre(String nombre);

    List<Receta> findByIngredientesReceta_Ingrediente_Nombre(String nombre);

    @Query("SELECT r FROM Receta r JOIN r.ingredientesReceta ri JOIN ri.ingrediente i WHERE LOWER(i.nombre) = LOWER(:nombre)")
    List<Receta> buscarPorIngredienteNombre(@Param("nombre") String nombre);

    @Query(value = "SELECT r.* FROM recetas r " +
            "JOIN ingredientes_receta ir ON r.id = ir.id_receta " +
            "JOIN ingredientes i ON ir.id_ingrediente = i.id " +
            "WHERE LOWER(i.nombre) = LOWER(:nombre)", nativeQuery = true)
    List<Receta> buscarPorIngredienteNombreNativo(@Param("nombre") String nombre);


    @Query("SELECT r FROM Receta r JOIN r.preferencias p WHERE p.nombre = :nombre")
    List<Receta> buscarPorPreferenciaNombre(@Param("nombre") String nombre);


    @Query(value = "SELECT r.* FROM recetas r JOIN megusta m ON r.id = m.receta_id WHERE m.usuario_id = :usuarioId", nativeQuery = true)
    List<Receta> findRecetasFavoritasByUsuarioId(@Param("usuarioId") int usuarioId);


}