package org.example.smartback.servicios;

import org.example.smartback.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import jakarta.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService service;

    @Test
    public void guardarTest() {
        // GIVEN
        Usuario u = new Usuario();
        u.setNombre("TestUser");
        u.setContraseña("12345");
        u.setEmail("testeo@mail.com");

        // WHEN
        Usuario guardado = service.guardar(u);

        // THEN
        assertNotNull(guardado, "El usuario no fue guardado");
        assertNotEquals("12345", guardado.getContraseña(), "La contraseña debería haber sido cifrada");
        assertEquals("testeo@mail.com", guardado.getEmail());
    }

    @Test
    public void eliminarTest() {
        // GIVEN
        Usuario u = new Usuario();
        u.setNombre("Borrame");
        u.setContraseña("123");
        u.setEmail("borrar@mail.com");
        Usuario guardado = service.guardar(u);

        // Usamos el ID generado
        int idABorrar = guardado.getId();

        // WHEN
        service.eliminar(idABorrar);

        // THEN
        Usuario buscado = service.obtenerPorId(idABorrar);
        assertNull(buscado, "El usuario debería ser null tras eliminarlo");
    }

    @Test
    public void listarTest() {
        // GIVEN
        Usuario u1 = new Usuario();
        u1.setNombre("Chef Uno");
        u1.setEmail("chef1@smartchef.com");
        u1.setContraseña("pass1");
        service.guardar(u1);

        Usuario u2 = new Usuario();
        u2.setNombre("Chef Dos");
        u2.setEmail("chef2@smartchef.com");
        u2.setContraseña("pass2");
        service.guardar(u2);

        // WHEN
        List<Usuario> lista = service.listar();

        // THEN
        assertNotNull(lista);
        // Usamos >= 2 por si ya hay datos previos en la BD de pruebas
        assertTrue(lista.size() >= 2, "La lista debería tener al menos 2 usuarios");
    }
}