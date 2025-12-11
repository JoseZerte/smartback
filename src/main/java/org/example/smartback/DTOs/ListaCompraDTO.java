package org.example.smartback.DTOs;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaCompraDTO {
    private int id;
    private String nombre;
    private LocalDateTime fecha_creacion;
    private int usuarioId;
    private List<ItemListaCompraDTO> items;
}

