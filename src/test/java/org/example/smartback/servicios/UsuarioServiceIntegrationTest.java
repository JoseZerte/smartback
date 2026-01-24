package org.example.smartback.servicios;

import org.example.smartback.model.Usuario;
import org.example.smartback.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceIntegrationTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    // REGISTRAR (CASO POSITIVO) ---
    @Test
    public void guardarUsuarioPositivoTest() {
        // GIVEN: Usuario con contraseña plana
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setContraseña("12345");

        // Simulamos el comportamiento del PasswordEncoder y el Repository
        when(passwordEncoder.encode("12345")).thenReturn("$2a$10$cifrado");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN: Ejecutamos el guardado
        Usuario resultado = usuarioService.guardar(usuario);

        // THEN: Validamos que el metodo cunmpliio su fuincion
        assertNotNull(resultado);
        assertEquals("$2a$10$cifrado", resultado.getContraseña());
        verify(passwordEncoder, times(1)).encode("12345");
        verify(usuarioRepository, times(1)).save(usuario);
    }

    // REGISTRAR (CASO NEGATIVO / BORDE) ---
    @Test
    public void guardarUsuarioSinPasswordTest() {
        // GIVEN: usuario con contraseña nula
        Usuario usuario = new Usuario();
        usuario.setNombre("SinPass");
        usuario.setContraseña(null);

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // WHEN: Ejecutamos el guardado
        Usuario resultado = usuarioService.guardar(usuario);

        // THEN: El servicio no debe intentar cifrar un nulo (evita NullPointerException)
        assertNotNull(resultado);
        verify(passwordEncoder, never()).encode(anyString());
        verify(usuarioRepository).save(usuario);
    }

    // OBTENER POR ID (CASO NEGATIVO)
    @Test
    public void obtenerUsuarioInexistenteTest() {
        // GIVEN: repositorio no encuentra nada para el ID 99
        when(usuarioRepository.findById(99)).thenReturn(Optional.empty());

        // WHEN: consultamos el servicio
        Usuario resultado = usuarioService.obtenerPorId(99);

        // THEN: comprobamos que el caso de uso devuelve null correctamente
        assertNull(resultado);
        verify(usuarioRepository, times(1)).findById(99);
    }
}