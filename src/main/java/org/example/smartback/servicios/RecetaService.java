package org.example.smartback.servicios;

import jakarta.transaction.Transactional;
import org.example.smartback.DTOs.RecetaDTO;
import org.example.smartback.model.Ingrediente;
import org.example.smartback.model.Receta;
import org.example.smartback.model.RecetaIngrediente;
import org.example.smartback.repository.IngredienteRepository;
import org.example.smartback.repository.RecetaIngredienteRepository;
import org.example.smartback.repository.RecetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecetaService {

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private RecetaIngredienteRepository recetaIngredienteRepository;

    @Transactional
    public Receta crearRecetaConIngredientes(Receta receta, List<RecetaDTO.IngredienteDTO> ingredientesDto) {

        if (receta.getFecha_creacion() == null) {
            receta.setFecha_creacion(LocalDateTime.now());
        }

        Receta recetaGuardada = recetaRepository.save(receta);
        System.out.println(" Receta guardada: " + recetaGuardada.getTitulo());

        if (ingredientesDto != null && !ingredientesDto.isEmpty()) {

            for (RecetaDTO.IngredienteDTO ingDto : ingredientesDto) {

                Ingrediente ingrediente = ingredienteRepository.findByNombre(ingDto.getNombre())
                        .orElseGet(() -> {
                            Ingrediente nuevo = new Ingrediente();
                            nuevo.setNombre(ingDto.getNombre());
                            nuevo.setTipo("Generico");
                            return ingredienteRepository.save(nuevo);
                        });

                RecetaIngrediente relacion = new RecetaIngrediente();
                relacion.setReceta(recetaGuardada);
                relacion.setIngrediente(ingrediente);
                relacion.setCantidad(ingDto.getCantidad());
                relacion.setUnidad(ingDto.getUnidad());

                recetaIngredienteRepository.save(relacion);
                System.out.println("🔗 Relación guardada: " + ingDto.getNombre());
            }
        }

        return recetaGuardada;
    }

    public List<Receta> buscarRecetasConFiltros(String categoria, String ingrediente, String preferencia) {

        if (categoria != null && !categoria.isEmpty()) {
            return recetaRepository.findByCategoriaNombre(categoria);
        }

        if (ingrediente != null && !ingrediente.isEmpty()) {
            return recetaRepository.buscarPorIngredienteNombreNativo(ingrediente);
        }

        if (preferencia != null && !preferencia.isEmpty()) {
            return recetaRepository.findByPreferencias_Nombre(preferencia);
        }

        return recetaRepository.findAll();
    }

    public List<Receta> listar() {
        return recetaRepository.findAll();
    }

    public Receta obtenerPorId(int id) {
        return recetaRepository.findById(id).orElse(null);
    }


    @Transactional
    public Receta actualizar(Receta receta) {
        return recetaRepository.save(receta);
    }

    public void eliminar(int id) {
        recetaRepository.deleteById(id);
    }
}