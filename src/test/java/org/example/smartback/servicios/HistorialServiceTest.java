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
public class HistorialServiceTest {

    @Autowired
    private HistorialService historialService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // HISTORIAL DE COCINADO

    @Test
    public void registrarHistorialPositivoTest() {
        // GIVEN
        Usuario u = crearUsuario("historial@test.com");
        Receta r = crearReceta("Tortitas", u);

        Historial h = new Historial();
        h.setUsuario(u);
        h.setReceta(r);
        h.setComentario("Salieron perfectas");
        // aseguramos fecha si el servicio no la pone automáticamente
        if(h.getFecha_cocinado() == null) h.setFecha_cocinado(LocalDateTime.now());

        // WHEN
        Historial guardado = historialService.guardar(h);

        // THEN
        assertNotNull(guardado.getId());
        assertEquals("Salieron perfectas", guardado.getComentario());
    }

    @Test
    public void listarHistorialPorUsuarioTest() {
        // GIVEN
        Usuario u = crearUsuario("lista_h@test.com");
        Receta r1 = crearReceta("Receta 1", u);
        Receta r2 = crearReceta("Receta 2", u);

        // registramos dos entradas en el historial
        registrarEntrada(u, r1, "Primera vez");
        registrarEntrada(u, r2, "Segunda vez");

        // WHEN
        List<Historial> lista = historialService.listarPorUsuario(u.getId());

        // THEN
        assertEquals(2, lista.size(), "El usuario debería tener 2 registros en su historial");
    }

    @Test
    public void listarHistorialVacioTest() {
        // GIVEN: un usuario nuevo sin historial
        Usuario u = crearUsuario("nuevo_h@test.com");

        // WHEN
        List<Historial> lista = historialService.listarPorUsuario(u.getId());

        // THEN
        assertTrue(lista.isEmpty(), "La lista debería estar vacía para un usuario nuevo");
    }

    @Test
    public void registrarHistorialInvalidoTest() {
        // GIVEN: un historial sin usuario ni receta (campos obligatorios)
        Historial hInvalido = new Historial();

        // THEN: deberia lanzar una excepcion
        assertThrows(Exception.class, () -> {
            historialService.guardar(hInvalido);
        });
    }

    // (REUTILIZABLES Y SEGUROS) ---

    private void registrarEntrada(Usuario u, Receta r, String comentario) {
        Historial h = new Historial();
        h.setUsuario(u);
        h.setReceta(r);
        h.setComentario(comentario);
        h.setFecha_cocinado(LocalDateTime.now());
        historialService.guardar(h);
    }

    private Usuario crearUsuario(String email) {
        Usuario u = new Usuario();
        u.setNombre("Chef User");
        u.setEmail(email);
        u.setContraseña("1234");
        return usuarioRepository.save(u);
    }

    private Receta crearReceta(String titulo, Usuario autor) {
        Categoria cat = new Categoria();
        cat.setNombre("Cat " + titulo);
        categoriaRepository.save(cat);

        Receta r = new Receta();
        r.setTitulo(titulo);
        r.setDescripcion("Desc");
        r.setDificultad("Media");
        r.setTiempo_preparacion("15 min");
        r.setTipo_dieta("Omnivora");
        r.setImagen("img.jpg");
        r.setFecha_creacion(LocalDateTime.now());
        r.setUsuario(autor);
        r.setCategoria(cat);
        return recetaRepository.save(r);
    }
}