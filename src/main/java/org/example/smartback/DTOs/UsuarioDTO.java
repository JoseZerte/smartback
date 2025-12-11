package org.example.smartback.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private String avatar;
    private String fecha_registr;
}
