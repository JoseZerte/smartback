package org.example.smartback.Controllers;

import org.example.smartback.DTOs.ListaCompraDTO;
import org.example.smartback.DTOs.ListaCompraRequest;
import org.example.smartback.mapper.ListaCompraMapper;
import org.example.smartback.model.ListaCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.example.smartback.servicios.ListaCompraService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/listas-compra")
public class ListaCompraController {

    @Autowired
    private ListaCompraService listaCompraService;

    @PostMapping
    public ListaCompraDTO crearLista(@RequestBody ListaCompraRequest request) {

        ListaCompra listaCreada = listaCompraService.generarListaDesdeReceta(
                request.getRecetaId(),
                request.getUsuarioId()
        );

        if (listaCreada == null) {
            return null;
        }

        return ListaCompraMapper.toDTO(listaCreada);
    }

    @GetMapping
    public List<ListaCompraDTO> listarTodas() {
        return listaCompraService.listar().stream()
                .map(ListaCompraMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<ListaCompraDTO> listarPorUsuario(@PathVariable int usuarioId) {
        return listaCompraService.listarPorUsuario(usuarioId).stream()
                .map(ListaCompraMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ListaCompraDTO obtener(@PathVariable int id) {
        ListaCompra lista = listaCompraService.obtenerPorId(id);
        return ListaCompraMapper.toDTO(lista);
    }
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) {
        listaCompraService.eliminar(id);
    }
}