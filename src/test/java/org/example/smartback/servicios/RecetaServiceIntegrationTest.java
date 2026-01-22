package org.example.smartback.servicios;

import org.example.smartback.DTOs.RecetaDTO;
import org.example.smartback.model.Ingrediente;
import org.example.smartback.model.Receta;
import org.example.smartback.model.RecetaIngrediente;
import org.example.smartback.repository.IngredienteRepository;
import org.example.smartback.repository.RecetaIngredienteRepository;
import org.example.smartback.repository.RecetaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecetaServiceIntegrationTest {

    @Mock
    private RecetaRepository recetaRepository;

    @Mock
    private IngredienteRepository ingredienteRepository;

    @Mock
    private RecetaIngredienteRepository recetaIngredienteRepository;

    @InjectMocks
    private RecetaService recetaService;

    // --- TEST UNITARIO 4: CREAR RECETA (CASO POSITIVO - LÓGICA COMPLEJA) ---
    @Test
    public void crearRecetaCompletaTest() {
        // GIVEN: Una receta y un DTO de ingrediente
        Receta receta = new Receta();
        receta.setTitulo("Pasta");

        RecetaDTO.IngredienteDTO ingDto = new RecetaDTO.IngredienteDTO();
        ingDto.setNombre("Tomate");
        ingDto.setCantidad(2);
        ingDto.setUnidad("Kg");
        List<RecetaDTO.IngredienteDTO> ingredientesDto = List.of(ingDto);

        // Simulamos el guardado de la receta
        when(recetaRepository.save(any(Receta.class))).thenReturn(receta);
        // Simulamos que el ingrediente no existe y se crea uno nuevo
        when(ingredienteRepository.findByNombre("Tomate")).thenReturn(Optional.empty());
        when(ingredienteRepository.save(any(Ingrediente.class))).thenAnswer(i -> i.getArgument(0));

        // WHEN: Ejecutamos la creación
        Receta resultado = recetaService.crearRecetaConIngredientes(receta, ingredientesDto);

        // THEN: Validamos la lógica relacional
        assertNotNull(resultado);
        verify(recetaRepository, times(1)).save(receta); // Guardó la receta
        verify(ingredienteRepository, times(1)).save(any(Ingrediente.class)); // Creó el ingrediente
        verify(recetaIngredienteRepository, times(1)).save(any(RecetaIngrediente.class)); // Creó la relación
    }

    // --- TEST UNITARIO 5: BUSCAR CON FILTROS (CASO POSITIVO) ---
    @Test
    public void buscarPorCategoriaTest() {
        // GIVEN: Simulamos que existen recetas en una categoría
        List<Receta> recetasFalsas = List.of(new Receta(), new Receta());
        when(recetaRepository.findByCategoriaNombre("Italiana")).thenReturn(recetasFalsas);

        // WHEN: Filtramos solo por categoría
        List<Receta> resultado = recetaService.buscarRecetasConFiltros("Italiana", null, null);

        // THEN: Verificamos que llamó al método correcto del repositorio
        assertEquals(2, resultado.size());
        verify(recetaRepository, times(1)).findByCategoriaNombre("Italiana");
        verify(recetaRepository, never()).findAll(); // No debería traer todas si hay filtro
    }

    // --- TEST UNITARIO 6: VER DETALLE (CASO NEGATIVO) ---
    @Test
    public void obtenerRecetaInexistenteTest() {
        // GIVEN: El ID 500 no existe
        when(recetaRepository.findById(500)).thenReturn(Optional.empty());

        // WHEN
        Receta resultado = recetaService.obtenerPorId(500);

        // THEN
        assertNull(resultado, "Debe retornar null si la receta no existe");
        verify(recetaRepository).findById(500);
    }
}