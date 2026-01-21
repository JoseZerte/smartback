package org.example.smartback.servicios;

import jakarta.transaction.Transactional;
import org.example.smartback.model.*;
import org.example.smartback.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class EstadisticasServiceTest {

    @Autowired
    private EstadisticasService estadisticasService;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MeGustaRepository meGustaRepository;

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private RecetaIngredienteRepository recetaIngredienteRepository;

    // --- EP 10: TOP 5 INGREDIENTES ---

    @Test
    public void top5IngredientesPositivoTest() {
        // GIVEN: Creamos un ingrediente y lo vinculamos a una receta para que aparezca en el top
        Usuario u = crearUsuario("stats1@test.com");
        Receta r = crearReceta("Receta Stats", u);
        Ingrediente ing = crearIngrediente("Sal");
        vincularIngrediente(r, ing);

        // WHEN
        List<Map<String, Object>> top = estadisticasService.top5Ingredientes();

        // THEN
        assertNotNull(top);
        assertFalse(top.isEmpty(), "Debería haber al menos un ingrediente en el top");
        assertEquals("Sal", top.get(0).get("ingrediente"));
    }

    @Test
    public void top5IngredientesVacioTest() {
        // GIVEN: BD limpia (gracias al @Transactional)
        // WHEN
        List<Map<String, Object>> top = estadisticasService.top5Ingredientes();
        // THEN
        assertTrue(top.isEmpty(), "Si no hay ingredientes, la lista debe estar vacía");
    }

    // --- EP 11: USUARIO POPULAR ---

    @Test
    public void usuarioPopularPositivoTest() {
        // GIVEN
        Usuario u = crearUsuario("pro@test.com");
        Receta r = crearReceta("Receta Popular", u);

        // Simulamos un Like
        MeGusta like = new MeGusta();
        like.setUsuario(u);
        like.setReceta(r);
        meGustaRepository.save(like);

        // WHEN
        Map<String, Object> popular = estadisticasService.usuarioPopular();

        // THEN
        assertNotNull(popular, "Debería devolver un mapa con el usuario");
        assertEquals("Chef User", popular.get("usuario"));
        assertTrue((Long)popular.get("likesTotales") >= 1);
    }

    @Test
    public void usuarioPopularVacioTest() {
        // WHEN: No hay likes ni usuarios
        Map<String, Object> popular = estadisticasService.usuarioPopular();

        // THEN: Dependiendo de tu implementación, puede ser null o un mapa vacío
        // Ajusta esto según lo que devuelva tu service (normalmente null si no hay resultados)
        assertTrue(popular == null || popular.isEmpty());
    }

    // --- MÉTODOS DE AYUDA (BLINDADOS) ---

    private Usuario crearUsuario(String email) {
        Usuario u = new Usuario();
        u.setNombre("Chef User");
        u.setEmail(email);
        u.setContraseña("123");
        return usuarioRepository.save(u);
    }

    private Receta crearReceta(String titulo, Usuario autor) {
        Categoria cat = new Categoria();
        cat.setNombre("Cat " + titulo);
        categoriaRepository.save(cat);

        Receta r = new Receta();
        r.setTitulo(titulo);
        r.setDescripcion("Desc");
        r.setDificultad("Baja");
        r.setTiempo_preparacion("5m");
        r.setTipo_dieta("Variada");
        r.setImagen("img.jpg");
        r.setFecha_creacion(LocalDateTime.now());
        r.setUsuario(autor);
        r.setCategoria(cat);
        return recetaRepository.save(r);
    }

    private Ingrediente crearIngrediente(String nombre) {
        Ingrediente ing = new Ingrediente();
        ing.setNombre(nombre);
        ing.setTipo("Generico");
        return ingredienteRepository.save(ing);
    }

    private void vincularIngrediente(Receta r, Ingrediente i) {
        RecetaIngrediente ri = new RecetaIngrediente();
        ri.setReceta(r);
        ri.setIngrediente(i);
        ri.setCantidad(1);
        ri.setUnidad("pizca");
        recetaIngredienteRepository.save(ri);
    }
}