package org.example.smartback.mapper;

import org.example.smartback.DTOs.ItemListaCompraDTO;
import org.example.smartback.DTOs.ListaCompraDTO;
import org.example.smartback.model.ItemListaCompra;
import org.example.smartback.model.ListaCompra;
import org.example.smartback.model.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public class ListaCompraMapper {

    public static ListaCompraDTO toDTO(ListaCompra lista) {
        List<ItemListaCompraDTO> items = lista.getItems().stream()
                .map(ItemListaCompraMapper::toDTO)
                .collect(Collectors.toList());

        return new ListaCompraDTO(
                lista.getId(),
                lista.getNombre(),
                lista.getFecha_creacion(),
                lista.getUsuario().getId(),
                items
        );
    }

    public static ListaCompra toEntity(ListaCompraDTO dto, Usuario usuario, List<ItemListaCompra> items) {
        ListaCompra lista = new ListaCompra();
        lista.setId(dto.getId());
        lista.setNombre(dto.getNombre());
        lista.setFecha_creacion(dto.getFecha_creacion());
        lista.setUsuario(usuario);
        lista.setItems(items.stream().collect(Collectors.toSet()));
        return lista;
    }
}
