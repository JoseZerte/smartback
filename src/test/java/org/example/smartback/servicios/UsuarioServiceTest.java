package org.example.smartback.servicios;
import org.example.smartback.model.Usuario; // ESTA ES LA CLAVE
import static org.junit.jupiter.api.Assertions.*; // Para poder usar assertNotNull, assertEquals...

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class UsuarioServiceTest {


    @Autowired
    private UsuarioService service;


    @Test
    public void guardarTest() {
// esto es el GIVEN que es la preparacion y datos de prueba
        Usuario u = new Usuario();
        u.setNombre("TestUser");
        u.setContraseña("12345");
        u.setEmail("testeo@mail.com");

// esto es el WHEN que es la accion a probar





    }

}
