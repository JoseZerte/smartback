package org.example.smartback.servicios;

import org.example.smartback.model.MeGusta;
import org.example.smartback.model.Receta;
import org.example.smartback.model.Usuario;
import org.example.smartback.repository.MeGustaRepository;
import org.example.smartback.repository.RecetaRepository;
import org.example.smartback.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MeGustaServiceIntegrationTest {

    @Mock
    private MeGustaRepository meGustaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RecetaRepository recetaRepository;

    @InjectMocks
    private MeGustaService meGustaService;

    // --- TEST UNITARIO 7: MARCAR FAVORITA (CASO POSITIVO) ---
    @Test
    public void marcarFavoritaPositivoTest() {
        // GIVEN: Usuario y receta existen, y no es favorita todavía
        Usuario u = new Usuario();
        Receta r = new Receta();

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(u));
        when(recetaRepository.findById(10)).thenReturn(Optional.of(r));
        when(meGustaRepository.findByUsuarioIdAndRecetaId(1, 10)).thenReturn(null);

        // WHEN: Ejecutamos la acción
        String resultado = meGustaService.marcarComoFavorita(1, 10);

        // THEN: Verificamos el mensaje y que se guardó
        assertEquals("Receta marcada como favorita", resultado);
        verify(meGustaRepository, times(1)).save(any(MeGusta.class));
    }

    // --- TEST UNITARIO 8: MARCAR FAVORITA (CASO NEGATIVO - DUPLICADO) ---
    @Test
    public void marcarFavoritaDuplicadaTest() {
        // GIVEN: El repositorio ya encuentra un registro previo
        Usuario u = new Usuario();
        Receta r = new Receta();
        MeGusta yaExistente = new MeGusta();

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(u));
        when(recetaRepository.findById(10)).thenReturn(Optional.of(r));
        when(meGustaRepository.findByUsuarioIdAndRecetaId(1, 10)).thenReturn(yaExistente);

        // WHEN
        String resultado = meGustaService.marcarComoFavorita(1, 10);

        // THEN: Verificamos que devuelve el aviso y NO guarda nada nuevo
        assertEquals("Ya es favorita", resultado);
        verify(meGustaRepository, never()).save(any(MeGusta.class));
    }

    // --- TEST UNITARIO 9: MARCAR FAVORITA (CASO NEGATIVO - NO EXISTE) ---
    @Test
    public void marcarFavoritaNoEncontradaTest() {
        // GIVEN: El usuario no existe en la base de datos
        when(usuarioRepository.findById(999)).thenReturn(Optional.empty());

        // WHEN
        String resultado = meGustaService.marcarComoFavorita(999, 10);

        // THEN
        assertEquals("No encontrado", resultado);
        verify(meGustaRepository, never()).save(any(MeGusta.class));
    }
}