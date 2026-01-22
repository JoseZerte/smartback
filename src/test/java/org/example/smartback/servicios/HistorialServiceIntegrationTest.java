package org.example.smartback.servicios;

import org.example.smartback.model.Historial;
import org.example.smartback.model.Receta;
import org.example.smartback.model.Usuario;
import org.example.smartback.repository.HistorialRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HistorialServiceIntegrationTest {

    @Mock
    private HistorialRepository historialRepository;

    @InjectMocks
    private HistorialService historialService;

    // --- TEST UNITARIO 13: REGISTRAR COCINADO (CASO POSITIVO) ---
    @Test
    public void registrarCocinadoPositivoTest() {
        // GIVEN: Un objeto historial preparado
        Historial h = new Historial();
        h.setComentario("Riquísimo");

        // Simulamos que el repositorio devuelve el mismo objeto al guardar
        when(historialRepository.save(any(Historial.class))).thenAnswer(i -> i.getArgument(0));

        // WHEN: Ejecutamos el guardado
        Historial resultado = historialService.guardar(h);

        // THEN: Validamos que se asignó la fecha automáticamente
        assertNotNull(resultado.getFecha_cocinado(), "La fecha de cocinado debe ser asignada por el servicio");
        assertEquals("Riquísimo", resultado.getComentario());
        verify(historialRepository, times(1)).save(h);
    }

    // --- TEST UNITARIO 14: CONSULTAR HISTORIAL (CASO POSITIVO) ---
    @Test
    public void listarHistorialUsuarioTest() {
        // GIVEN: Una lista con 2 registros para el usuario 1
        List<Historial> listaFalsa = new ArrayList<>();
        listaFalsa.add(new Historial());
        listaFalsa.add(new Historial());

        when(historialRepository.findByUsuarioId(1)).thenReturn(listaFalsa);

        // WHEN: Consultamos el historial del usuario 1
        List<Historial> resultado = historialService.listarPorUsuario(1);

        // THEN: Verificamos que devuelve el tamaño correcto
        assertEquals(2, resultado.size());
        verify(historialRepository, times(1)).findByUsuarioId(1);
    }

    // --- TEST UNITARIO 15: CONSULTAR HISTORIAL (CASO NEGATIVO / VACÍO) ---
    @Test
    public void listarHistorialVacioTest() {
        // GIVEN: El repositorio devuelve una lista vacía para un usuario nuevo
        when(historialRepository.findByUsuarioId(99)).thenReturn(new ArrayList<>());

        // WHEN: Consultamos
        List<Historial> resultado = historialService.listarPorUsuario(99);

        // THEN: Verificamos que no rompe y devuelve vacío
        assertTrue(resultado.isEmpty());
        verify(historialRepository).findByUsuarioId(99);
    }
}