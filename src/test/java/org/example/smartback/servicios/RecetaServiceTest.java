package org.example.smartback.servicios;

import jakarta.transaction.Transactional;
import org.example.smartback.DTOs.RecetaDTO;
import org.example.smartback.model.*;
import org.example.smartback.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
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

    @Autowired
    private RecetaRepository recetaRepository;

    // --- EP 2: CREAR RECETA (POST /recetas) ---

    @Test
    public void crearRecetaPositivoTest() {
        // GIVEN
        Usuario autor = crearUsuarioPrueba();
        Categoria cat = crearCategoriaPrueba("Almuerzo");

        Receta receta = new Receta();
        completarDatosReceta(receta, "Pasta Carbonara", autor, cat);

        List<RecetaDTO.IngredienteDTO> ings = new ArrayList<>();
        RecetaDTO.IngredienteDTO ing = new RecetaDTO.IngredienteDTO();
        ing.setNombre("Panceta");
        ing.setCantidad(100);
        ing.setUnidad("gramos");
        ings.add(ing);

        // WHEN
        Receta guardada = recetaService.crearRecetaConIngredientes(receta, ings);

        // THEN
        assertNotNull(guardada.getId());
        assertEquals("Pasta Carbonara", guardada.getTitulo());
    }

    @Test
    public void crearRecetaNegativoTest() {
        // GIVEN: Una receta totalmente vacía (sin título ni descripción)
        Receta recetaInvalida = new Receta();

        // THEN: El servicio debe petar al intentar guardar en el repo
        assertThrows(Exception.class, () -> {
            recetaService.crearRecetaConIngredientes(recetaInvalida, null);
        });
    }

    // --- EP 3: FILTROS (GET /recetas con filtros) ---

    @Test
    public void buscarFiltroPositivoTest() {
        // GIVEN
        Categoria cat = crearCategoriaPrueba("Vegano");
        Receta r = new Receta();
        completarDatosReceta(r, "Ensalada", crearUsuarioPrueba(), cat);
        recetaRepository.save(r);

        // WHEN
        List<Receta> resultados = recetaService.buscarRecetasConFiltros("Vegano", null, null);

        // THEN
        assertFalse(resultados.isEmpty(), "Debería encontrar la receta por categoría");
    }

    @Test
    public void buscarFiltroNegativoTest() {
        // WHEN: Buscamos algo que no existe
        List<Receta> resultados = recetaService.buscarRecetasConFiltros("Klingon", null, null);

        // THEN
        assertTrue(resultados.isEmpty(), "La lista debe estar vacía");
    }

    // --- EP 4: DETALLE (GET /recetas/{id}) ---

    @Test
    public void obtenerDetallePositivoTest() {
        // GIVEN
        Receta r = new Receta();
        completarDatosReceta(r, "Detalle", crearUsuarioPrueba(), crearCategoriaPrueba("Test"));
        Receta guardada = recetaRepository.save(r);

        // WHEN
        Receta encontrada = recetaService.obtenerPorId(guardada.getId());

        // THEN
        assertNotNull(encontrada);
        assertEquals("Detalle", encontrada.getTitulo());
    }

    @Test
    public void obtenerDetalleNegativoTest() {
        // WHEN: Buscamos un ID que no existe
        Receta encontrada = recetaService.obtenerPorId(9999);

        // THEN
        assertNull(encontrada);
    }

    // --- MÉTODOS DE APOYO (Para evitar errores de campos nulos) ---

    private void completarDatosReceta(Receta r, String titulo, Usuario u, Categoria c) {
        r.setTitulo(titulo);
        r.setDescripcion("Descripción obligatoria");
        r.setDificultad("Media");
        r.setTiempo_preparacion("20 min");
        r.setTipo_dieta("Estándar");
        r.setImagen("imagen.jpg");
        // ESTA LÍNEA ES LA QUE FALTA:
        r.setFecha_creacion(java.time.LocalDateTime.now());
        r.setUsuario(u);
        r.setCategoria(c);
    }

    private Usuario crearUsuarioPrueba() {
        Usuario u = new Usuario();
        u.setNombre("Test User");
        u.setEmail("user" + System.currentTimeMillis() + "@test.com");
        u.setContraseña("pass");
        return usuarioRepository.save(u);
    }

    private Categoria crearCategoriaPrueba(String nombre) {
        Categoria c = new Categoria();
        c.setNombre(nombre);
        return categoriaRepository.save(c);
    }
}