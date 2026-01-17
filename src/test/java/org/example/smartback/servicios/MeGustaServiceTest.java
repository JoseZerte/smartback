package org.example.smartback.servicios;

import jakarta.transaction.Transactional;
import org.example.smartback.model.Categoria;
import org.example.smartback.model.MeGusta;
import org.example.smartback.model.Receta;
import org.example.smartback.model.Usuario;
import org.example.smartback.repository.CategoriaRepository;
import org.example.smartback.repository.RecetaRepository;
import org.example.smartback.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MeGustaServiceTest {

    @Autowired
    private MeGustaService meGustaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    public void marcarFavoritaTest() {
        // GIVEN: Necesitamos un usuario y una receta reales
        Usuario u = new Usuario();
        u.setNombre("Fan de Cocina");
        u.setEmail("fan@test.com");
        u.setContraseña("pass123");
        Usuario usuarioGuardado = usuarioRepository.save(u);

        Categoria cat = new Categoria();
        cat.setNombre("Postres");
        categoriaRepository.save(cat);

        Receta r = new Receta();
        r.setTitulo("Brownie");
        r.setDescripcion("Muy dulce");
        r.setDificultad("Fácil");
        r.setImagen("brownie.jpg");
        r.setTiempo_preparacion("30 min");
        r.setTipo_dieta("Omnívora");
        r.setUsuario(usuarioGuardado);
        r.setCategoria(cat);

        // ESTA ES LA LÍNEA QUE FALTABA:
        r.setFecha_creacion(java.time.LocalDateTime.now());

        Receta recetaGuardada = recetaRepository.save(r);

        // WHEN: Primer marcado
        String resultado1 = meGustaService.marcarComoFavorita(usuarioGuardado.getId(), recetaGuardada.getId());

        // WHEN: Intento de duplicado
        String resultado2 = meGustaService.marcarComoFavorita(usuarioGuardado.getId(), recetaGuardada.getId());

        // THEN: Verificaciones
        assertEquals("Receta marcada como favorita", resultado1);
        assertEquals("Ya es favorita", resultado2);

        List<MeGusta> favoritos = meGustaService.obtenerFavoritosDeUsuario(usuarioGuardado.getId());
        assertEquals(1, favoritos.size());
    }

    @Test
    public void marcarFavoritaNoEncontradaTest() {
        // WHEN: Intentamos marcar con IDs que no existen (Caso Negativo)
        String resultado = meGustaService.marcarComoFavorita(999, 888);

        // THEN
        assertEquals("No encontrado", resultado);
    }
}