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
public class ListaCompraServiceTest {

    @Autowired
    private ListaCompraService listaCompraService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private RecetaIngredienteRepository recetaIngredienteRepository;

    // ESTO ES LO QUE FALTABA (La inyección del repositorio de items)
    @Autowired
    private ItemListaCompraRepository itemListaCompraRepository;

    @Test
    public void generarListaDesdeRecetaTest() {
        // 1. GIVEN
        Usuario user = new Usuario();
        user.setNombre("Comprador");
        user.setEmail("buy@test.com");
        user.setContraseña("123");
        usuarioRepository.save(user);

        Categoria cat = new Categoria();
        cat.setNombre("Cena");
        categoriaRepository.save(cat);

        Ingrediente ing = new Ingrediente();
        ing.setNombre("Tomate");
        ing.setTipo("Verdura");
        ingredienteRepository.save(ing);

        Receta rec = new Receta();
        rec.setTitulo("Ensalada");
        rec.setDescripcion("Sana");
        rec.setDificultad("Baja");
        rec.setImagen("img.jpg");
        rec.setTiempo_preparacion("5 min");
        rec.setTipo_dieta("Vegana");
        rec.setFecha_creacion(LocalDateTime.now());
        rec.setUsuario(user);
        rec.setCategoria(cat);
        // INICIALIZAMOS LA LISTA si es necesario (evitar NullPointerException)
        if (rec.getIngredientesReceta() == null) {
            rec.setIngredientesReceta(new java.util.HashSet<>());
        }
        recetaRepository.save(rec);

        RecetaIngrediente ri = new RecetaIngrediente();
        ri.setReceta(rec);
        ri.setIngrediente(ing);
        ri.setCantidad(2);
        ri.setUnidad("piezas");
        recetaIngredienteRepository.save(ri);

        // AQUÍ EL TRUCO: Añadimos manualmente el ingrediente a la receta en memoria
        rec.getIngredientesReceta().add(ri);

        // 2. WHEN
        ListaCompra lista = listaCompraService.generarListaDesdeReceta(rec.getId(), user.getId());

        // 3. THEN
        assertNotNull(lista, "La lista no debería ser nula");

        List<ItemListaCompra> itemsEnDB = itemListaCompraRepository.findAll();

        // Ahora sí: esperamos que NO esté vacía
        assertFalse(itemsEnDB.isEmpty(), "Deberían haberse guardado items en la base de datos");

        assertEquals("Tomate", itemsEnDB.get(0).getIngrediente().getNombre());
    }

    @Test
    public void generarListaRecetaInexistenteTest() {
        Usuario user = new Usuario();
        user.setNombre("UserTest");
        user.setEmail("test@mail.com");
        user.setContraseña("123");
        usuarioRepository.save(user);

        // Aquí usamos 999 porque sabemos que no existe esa receta
        ListaCompra resultado = listaCompraService.generarListaDesdeReceta(999, user.getId());

        assertNull(resultado, "Debería devolver null si la receta no existe");
    }
}