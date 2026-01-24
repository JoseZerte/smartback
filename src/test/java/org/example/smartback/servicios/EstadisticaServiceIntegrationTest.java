package org.example.smartback.servicios;

import org.example.smartback.model.Ingrediente;
import org.example.smartback.model.MeGusta;
import org.example.smartback.model.RecetaIngrediente;
import org.example.smartback.model.Usuario;
import org.example.smartback.repository.IngredienteRepository;
import org.example.smartback.repository.MeGustaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstadisticaServiceIntegrationTest {

    @Mock
    private IngredienteRepository ingredienteRepository;

    @Mock
    private MeGustaRepository meGustaRepository;

    @InjectMocks
    private EstadisticasService estadisticasService;

    // TOP 5 INGREDIENTES
    @Test
    public void top5IngredientesPositivoTest() {
        // GIVEN: creamos ingredientes
        Ingrediente ing1 = new Ingrediente();
        ing1.setNombre("Sal");

        // relación para Sal (ID 1)
        RecetaIngrediente ri1 = new RecetaIngrediente();
        ri1.setId(1);
        Set<RecetaIngrediente> recetasSal = new HashSet<>();
        recetasSal.add(ri1);
        ing1.setRecetas(recetasSal); // Sal tiene 1

        Ingrediente ing2 = new Ingrediente();
        ing2.setNombre("Aceite");

        // relaciones para Aceite (ID distintos para que el Set los cuente)
        RecetaIngrediente ri2 = new RecetaIngrediente();
        ri2.setId(2);
        RecetaIngrediente ri3 = new RecetaIngrediente();
        ri3.setId(3);

        Set<RecetaIngrediente> recetasAceite = new HashSet<>();
        recetasAceite.add(ri2);
        recetasAceite.add(ri3);
        ing2.setRecetas(recetasAceite); // Aceite tiene 2

        // Los pasamos al mock
        when(ingredienteRepository.findAll()).thenReturn(Arrays.asList(ing1, ing2));

        // WHEN
        List<Map<String, Object>> resultado = estadisticasService.top5Ingredientes();

        // THEN
        assertNotNull(resultado);
        // Ahora Aceite (2) SIEMPRE será mayor que Sal (1)
        assertEquals("Aceite", resultado.get(0).get("ingrediente"), "Aceite debe ser el primero por tener más recetas");
        assertEquals(2, resultado.get(0).get("recetasUsadas"));
    }
    // USUARIO POPULAR
    @Test
    public void usuarioPopularPositivoTest() {
        // GIVEN: Usuarios y likes simulados
        Usuario u1 = new Usuario(); u1.setNombre("Chef Pro");
        Usuario u2 = new Usuario(); u2.setNombre("Chef Novato");

        MeGusta l1 = new MeGusta(); l1.setUsuario(u1);
        MeGusta l2 = new MeGusta(); l2.setUsuario(u1); // 2 likes para u1
        MeGusta l3 = new MeGusta(); l3.setUsuario(u2); // 1 like para u2

        when(meGustaRepository.findAll()).thenReturn(Arrays.asList(l1, l2, l3));

        // WHEN: buscamos el usuario popular
        Map<String, Object> resultado = estadisticasService.usuarioPopular();

        // THEN: El ganador debe ser Chef Pro
        assertNotNull(resultado);
        assertEquals("Chef Pro", resultado.get("usuario"));
        assertEquals(2L, resultado.get("likesTotales"));
    }

    // CASO NEGATIVO
    @Test
    public void estadisticasVaciasTest() {
        // GIVEN: Listas vacias en los repositorios
        when(ingredienteRepository.findAll()).thenReturn(new ArrayList<>());
        when(meGustaRepository.findAll()).thenReturn(new ArrayList<>());

        // WHEN
        List<Map<String, Object>> top = estadisticasService.top5Ingredientes();
        Map<String, Object> popular = estadisticasService.usuarioPopular();

        // THEN: debe devolver estructuras vacias
        assertTrue(top.isEmpty());
        assertTrue(popular.isEmpty());
        verify(ingredienteRepository).findAll();
        verify(meGustaRepository).findAll();
    }
}