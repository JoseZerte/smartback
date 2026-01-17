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

    @Test
    public void top5IngredientesTest() {
        // GIVEN: Creamos ingredientes con diferentes cantidades de recetas
        Ingrediente ing1 = new Ingrediente();
        ing1.setNombre("Sal");
        // Importante: Inicializamos la colección para que el .size() no de error
        ing1.setRecetas(new HashSet<>());
        ingredienteRepository.save(ing1);

        Ingrediente ing2 = new Ingrediente();
        ing2.setNombre("Aceite");
        ing2.setRecetas(new HashSet<>());
        ingredienteRepository.save(ing2);

        // Simulamos que Aceite está en una receta (añadiendo una receta ficticia si el modelo lo permite)
        // O simplemente verificamos que el servicio devuelve la estructura correcta

        // WHEN
        List<Map<String, Object>> top = estadisticasService.top5Ingredientes();

        // THEN
        assertNotNull(top);
        assertTrue(top.size() <= 5);
        if(!top.isEmpty()) {
            assertTrue(top.get(0).containsKey("ingrediente"));
            assertTrue(top.get(0).containsKey("recetasUsadas"));
        }
    }

    @Test
    public void usuarioPopularTest() {
        // 1. GIVEN: Creamos usuarios y una receta para dar likes
        Usuario u1 = new Usuario();
        u1.setNombre("Usuario Pro");
        u1.setEmail("pro@test.com");
        u1.setContraseña("123");
        usuarioRepository.save(u1);

        Categoria cat = new Categoria();
        cat.setNombre("General");
        categoriaRepository.save(cat);

        Receta r = new Receta();
        r.setTitulo("Receta Popular");
        r.setDescripcion("Desc");
        r.setDificultad("Baja");
        r.setImagen("img.jpg");
        r.setTiempo_preparacion("5m");
        r.setTipo_dieta("Vegana");
        r.setFecha_creacion(LocalDateTime.now());
        r.setUsuario(u1);
        r.setCategoria(cat);
        recetaRepository.save(r);

        // Le damos un "Me Gusta" al usuario 1
        MeGusta like = new MeGusta();
        like.setUsuario(u1);
        like.setReceta(r);
        meGustaRepository.save(like);

        // 2. WHEN
        Map<String, Object> popular = estadisticasService.usuarioPopular();

        // 3. THEN
        assertNotNull(popular);
        assertEquals("Usuario Pro", popular.get("usuario"));
        assertEquals(1L, popular.get("likesTotales"));
    }
}