package org.example.smartback.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeGustaDTO {
    private int id;
    private int usuarioId;
    private int recetaId;
}
