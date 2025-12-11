package org.example.smartback.Controllers;

import org.example.smartback.DTOs.HistorialDTO;
import org.example.smartback.mapper.HistorialMapper;
import org.example.smartback.model.Historial;
import org.example.smartback.model.Receta;
import org.example.smartback.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.example.smartback.servicios.HistorialService;
import org.example.smartback.servicios.RecetaService;
import org.example.smartback.servicios.UsuarioService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/historial-cocina")
public class HistorialController {

    @Autowired
    private HistorialService historialService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RecetaService recetaService;

    // POST: registrar una receta cocinada
    @PostMapping
    public HistorialDTO registrar(@RequestBody HistorialDTO historialDTO) {
        Usuario usuario = usuarioService.obtenerPorId(historialDTO.getUsuarioId());
        Receta receta = recetaService.obtenerPorId(historialDTO.getRecetaId());
        Historial historial = HistorialMapper.toEntity(historialDTO, usuario, receta);
        historial = historialService.guardar(historial);
        return HistorialMapper.toDTO(historial);
    }

    // GET: consultar historial de un usuario
    @GetMapping
    public List<HistorialDTO> obtenerPorUsuario(@RequestParam int usuarioId) {
        List<Historial> historialList = historialService.listarPorUsuario(usuarioId);
        return historialList.stream()
                .map(HistorialMapper::toDTO)
                .collect(Collectors.toList());
    }
}
