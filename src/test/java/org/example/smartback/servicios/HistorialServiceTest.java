package org.example.smartback.servicios;

import jakarta.transaction.Transactional;
import org.example.smartback.model.Categoria;
import org.example.smartback.model.Historial;
import org.example.smartback.model.Receta;
import org.example.smartback.model.Usuario;
import org.example.smartback.repository.CategoriaRepository;
import org.example.smartback.repository.RecetaRepository;
import org.example.smartback.repository.UsuarioRepository;
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

    @Test
    public void registrarYListarHistorialTest() {
        // 1. GIVEN: Usuario y Receta preparados
        Usuario u = new Usuario();
        u.setNombre("Cocinero");
        u.setEmail("cocina@test.com");
        u.setContraseña("pass");
        usuarioRepository.save(u);

        Categoria cat = new Categoria();
        cat.setNombre("Desayuno");
        categoriaRepository.save(cat);

        Receta r = new Receta();
        r.setTitulo("Tortitas");
        r.setDescripcion("Ricas");
        r.setDificultad("Fácil");
        r.setImagen("tortitas.jpg");
        r.setTiempo_preparacion("10 min");
        r.setTipo_dieta("Vegetariana");
        r.setFecha_creacion(LocalDateTime.now());
        r.setUsuario(u);
        r.setCategoria(cat);
        recetaRepository.save(r);

        // 2. WHEN: Registramos que el usuario ha cocinado la receta
        Historial h = new Historial();
        h.setUsuario(u);
        h.setReceta(r);
        h.setComentario("Me salieron un poco quemadas");

        Historial guardado = historialService.guardar(h);

        // 3. THEN: Verificaciones
        assertNotNull(guardado.getId());
        assertNotNull(guardado.getFecha_cocinado(), "El servicio debería asignar la fecha actual");
        assertEquals("Me salieron un poco quemadas", guardado.getComentario());

        // Verificamos que al listar por usuario aparezca (Endpoint 8)
        List<Historial> lista = historialService.listarPorUsuario(u.getId());
        assertFalse(lista.isEmpty());
        assertEquals(1, lista.size());
        assertEquals("Tortitas", lista.get(0).getReceta().getTitulo());
    }
}