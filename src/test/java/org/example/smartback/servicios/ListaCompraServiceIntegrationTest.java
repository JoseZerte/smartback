package org.example.smartback.servicios;

import org.example.smartback.model.*;
import org.example.smartback.repository.ItemListaCompraRepository;
import org.example.smartback.repository.ListaCompraRepository;
import org.example.smartback.repository.RecetaRepository;
import org.example.smartback.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListaCompraServiceIntegrationTest {

    @Mock
    private ListaCompraRepository listaCompraRepository;
    @Mock
    private RecetaRepository recetaRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private ItemListaCompraRepository itemListaCompraRepository;

    @InjectMocks
    private ListaCompraService listaCompraService;

    // GENERAR LISTA
    @Test
    public void generarListaNuevaTest() {
        // GIVEN: una receta con un ingrediente y un usuario sin lista previa
        Usuario usuario = new Usuario();
        usuario.setId(1);

        Receta receta = new Receta();
        receta.setId(10);

        RecetaIngrediente ri = new RecetaIngrediente();
        ri.setIngrediente(new Ingrediente());
        ri.setCantidad(2);
        receta.setIngredientesReceta(Set.of(ri));

        when(recetaRepository.findById(10)).thenReturn(Optional.of(receta));
        when(listaCompraRepository.findByUsuarioId(1)).thenReturn(new ArrayList<>()); // Lista vacía
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(listaCompraRepository.save(any(ListaCompra.class))).thenAnswer(i -> i.getArgument(0));

        // WHEN
        ListaCompra resultado = listaCompraService.generarListaDesdeReceta(10, 1);

        // THEN
        assertNotNull(resultado);
        assertEquals("Mi Lista de Compra", resultado.getNombre());
        verify(listaCompraRepository, times(1)).save(any(ListaCompra.class));// se crea la lista
        verify(itemListaCompraRepository, times(1)).save(any(ItemListaCompra.class)); // se guarda el ítem
    }

    // GENERAR LISTA
    @Test
    public void generarListaExistenteTest() {
        // GIVEN: el usuario ya tiene una lista creada
        Receta receta = new Receta();
        receta.setIngredientesReceta(new HashSet<>());

        ListaCompra listaExistente = new ListaCompra();
        listaExistente.setNombre("Lista Antigua");

        when(recetaRepository.findById(10)).thenReturn(Optional.of(receta));
        when(listaCompraRepository.findByUsuarioId(1)).thenReturn(List.of(listaExistente));

        // WHEN
        ListaCompra resultado = listaCompraService.generarListaDesdeReceta(10, 1);

        // THEN
        assertNotNull(resultado);
        assertEquals("Lista Antigua", resultado.getNombre());
        verify(listaCompraRepository, never()).save(any(ListaCompra.class)); // NO se crea otra lista
    }

    //GENERAR LISTA
    @Test
    public void generarListaRecetaNoExisteTest() {
        // GIVEN: La receta con ID 99 no existe
        when(recetaRepository.findById(99)).thenReturn(Optional.empty());

        // WHEN
        ListaCompra resultado = listaCompraService.generarListaDesdeReceta(99, 1);

        // THEN
        assertNull(resultado);
        verifyNoInteractions(itemListaCompraRepository); // No se debería intentar guardar nada
    }
}