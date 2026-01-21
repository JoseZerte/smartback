package org.example.smartback.servicios;

import jakarta.transaction.Transactional;
import org.example.smartback.model.*;
import org.example.smartback.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
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

    // --- EP 5: MARCAR FAVORITA (POST /favoritos) ---

    @Test
    public void marcarFavoritaPositivoTest() {
        Usuario u = crearUsuario("fan@test.com");
        Receta r = crearReceta("Brownie", u);

        // Caso 1: Marcar por primera vez
        String resultado = meGustaService.marcarComoFavorita(u.getId(), r.getId());
        assertEquals("Receta marcada como favorita", resultado);

        // Verificar que realmente existe en la lista del usuario
        List<MeGusta> favoritos = meGustaService.obtenerFavoritosDeUsuario(u.getId());
        assertEquals(1, favoritos.size());
    }

    @Test
    public void marcarFavoritaDuplicadaTest() {
        Usuario u = crearUsuario("duplicado@test.com");
        Receta r = crearReceta("Tarta", u);

        // Marcar dos veces
        meGustaService.marcarComoFavorita(u.getId(), r.getId());
        String resultado2 = meGustaService.marcarComoFavorita(u.getId(), r.getId());

        assertEquals("Ya es favorita", resultado2);
    }

    @Test
    public void marcarFavoritaInexistenteTest() {
        // IDs que no existen en la BD
        String resultado = meGustaService.marcarComoFavorita(999, 888);
        assertEquals("No encontrado", resultado);
    }

    // --- EP 6: LISTAR FAVORITOS (GET /favoritos/usuario/{id}) ---

    @Test
    public void listarFavoritosVacioTest() {
        Usuario u = crearUsuario("vacio@test.com");

        List<MeGusta> favoritos = meGustaService.obtenerFavoritosDeUsuario(u.getId());

        assertTrue(favoritos.isEmpty(), "Un usuario nuevo no debería tener favoritos");
    }

    // --- MÉTODOS DE AYUDA (BLINDADOS) ---

    private Usuario crearUsuario(String email) {
        Usuario u = new Usuario();
        u.setNombre("User Test");
        u.setEmail(email);
        u.setContraseña("123");
        return usuarioRepository.save(u);
    }

    private Receta crearReceta(String titulo, Usuario autor) {
        Categoria cat = new Categoria();
        cat.setNombre("Categoria " + titulo);
        categoriaRepository.save(cat);

        Receta r = new Receta();
        r.setTitulo(titulo);
        r.setDescripcion("Desc obligatoria");
        r.setDificultad("Media");
        r.setTiempo_preparacion("10 min");
        r.setTipo_dieta("Variada");
        r.setImagen("img.jpg");
        r.setFecha_creacion(LocalDateTime.now()); // Crucial para que no pete
        r.setUsuario(autor);
        r.setCategoria(cat);
        return recetaRepository.save(r);
    }
}