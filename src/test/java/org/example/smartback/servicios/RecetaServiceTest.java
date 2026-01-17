package org.example.smartback.servicios;

import jakarta.transaction.Transactional;
import org.example.smartback.DTOs.RecetaDTO;
import org.example.smartback.model.Categoria;
import org.example.smartback.model.Receta;
import org.example.smartback.model.Usuario;
import org.example.smartback.repository.CategoriaRepository;
import org.example.smartback.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class RecetaServiceTest {

    @Autowired
    private RecetaService recetaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    public void crearRecetaConIngredientesTest() {
        // GIVEN: Necesitamos un usuario y categoría reales para que no falle la FK
        Usuario autor = new Usuario();
        autor.setNombre("Chef Test");
        autor.setEmail("chef@test.com");
        autor.setContraseña("12345");
        usuarioRepository.save(autor);

        Categoria cat = new Categoria();
        cat.setNombre("Almuerzo");
        categoriaRepository.save(cat);

        // Preparamos la receta
        Receta receta = new Receta();
        receta.setTitulo("Pasta Carbonara");
        receta.setDescripcion("Receta italiana");
        receta.setDificultad("Media");
        receta.setImagen("pasta.jpg");
        receta.setTiempo_preparacion("15 min");
        receta.setTipo_dieta("Mediterránea");
        receta.setUsuario(autor);
        receta.setCategoria(cat);

        // Preparamos los ingredientes DTO
        RecetaDTO.IngredienteDTO ing = new RecetaDTO.IngredienteDTO();
        ing.setNombre("Panceta");
        ing.setCantidad(100);
        ing.setUnidad("gramos");

        List<RecetaDTO.IngredienteDTO> listaIng = new ArrayList<>();
        listaIng.add(ing);

        // WHEN: Ejecutamos el servicio
        Receta guardada = recetaService.crearRecetaConIngredientes(receta, listaIng);

        // THEN: Verificaciones (Test Unitario 2 validado)
        assertNotNull(guardada.getId(), "La receta debería tener un ID");
        assertEquals("Pasta Carbonara", guardada.getTitulo());
        assertNotNull(guardada.getFecha_creacion(), "La fecha de creación debe autogenerarse");
    }

    @Test
    public void buscarRecetaInexistenteTest() {
        // GIVEN

        // WHEN: Buscamos por una categoría que no existe (Caso Negativo)
        List<Receta> resultados = recetaService.buscarRecetasConFiltros("Inexistente", null, null);

        // THEN Verificamos que la lógica maneja el vacío correctamente
        assertNotNull(resultados);
        assertTrue(resultados.isEmpty(), "La lista debería estar vacía para filtros sin coincidencia");
    }

    @Test
    public void obtenerPorIdTest() {

        Receta r = new Receta();
        r.setTitulo("Sopa");


        // WHEN & THEN
        Receta encontrada = recetaService.obtenerPorId(999); // ID que no existe
        assertNull(encontrada, "Debería devolver null si el ID no existe");
    }
}