package org.example.smartback.Controllers;

import org.example.smartback.DTOs.ListaCompraDTO;
import org.example.smartback.mapper.ItemListaCompraMapper;
import org.example.smartback.mapper.ListaCompraMapper;
import org.example.smartback.model.Ingrediente;
import org.example.smartback.model.ItemListaCompra;
import org.example.smartback.model.ListaCompra;
import org.example.smartback.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.example.smartback.servicios.IngredienteService;
import org.example.smartback.servicios.ListaCompraService;
import org.example.smartback.servicios.UsuarioService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/listas-compra")
public class ListaCompraController {

    @Autowired
    private ListaCompraService listaCompraService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private IngredienteService ingredienteService;

    // POST: crear lista de compra
    @PostMapping
    public ListaCompraDTO crearLista(@RequestBody ListaCompraDTO listaDTO) {
        Usuario usuario = usuarioService.obtenerPorId(listaDTO.getUsuarioId());

        // Convertir ItemsDTO → Items entidad
        Set<ItemListaCompra> items = listaDTO.getItems().stream()
                .map(itemDTO -> {
                    Ingrediente ingrediente = ingredienteService.obtenerPorId(itemDTO.getIngredienteId());
                    return ItemListaCompraMapper.toEntity(itemDTO, ingrediente, null); // lista será asignada luego
                })
                .collect(Collectors.toSet());

        // Crear la lista de compra
        ListaCompra lista = ListaCompraMapper.toEntity(listaDTO, usuario, List.copyOf(items));

        // Asignar la lista a cada item (solución al error de lambda)
        for (ItemListaCompra item : items) {
            item.setListaCompra(lista);
        }

        lista = listaCompraService.guardar(lista);
        return ListaCompraMapper.toDTO(lista);
    }

    // GET: listar todas las listas
    @GetMapping
    public List<ListaCompraDTO> listarTodas() {
        return listaCompraService.listar().stream()
                .map(ListaCompraMapper::toDTO)
                .collect(Collectors.toList());
    }

    // GET: listar listas por usuario
    @GetMapping("/usuario/{usuarioId}")
    public List<ListaCompraDTO> listarPorUsuario(@PathVariable int usuarioId) {
        return listaCompraService.listarPorUsuario(usuarioId).stream()
                .map(ListaCompraMapper::toDTO)
                .collect(Collectors.toList());
    }

    // GET: obtener lista por ID
    @GetMapping("/{id}")
    public ListaCompraDTO obtener(@PathVariable int id) {
        ListaCompra lista = listaCompraService.obtenerPorId(id);
        return ListaCompraMapper.toDTO(lista);
    }

    // DELETE: eliminar lista
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) {
        listaCompraService.eliminar(id);
    }
}
