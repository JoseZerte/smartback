package org.example.smartback.Controllers;

import org.example.smartback.DTOs.RecetaDTO;
import org.example.smartback.mapper.RecetaMapper;
import org.example.smartback.model.Categoria;
import org.example.smartback.model.Receta;
import org.example.smartback.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.example.smartback.servicios.RecetaService;
import org.example.smartback.servicios.MeGustaService;
import org.example.smartback.servicios.UsuarioService;
import org.example.smartback.servicios.CategoriaService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recetas")
public class RecetaController {

    @Autowired
    private RecetaService recetaService;

    @Autowired
    private MeGustaService meGustaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CategoriaService categoriaService;

    // POST: agregar receta
    @PostMapping
    public RecetaDTO crear(@RequestBody RecetaDTO recetaDTO) {
        Usuario usuario = usuarioService.obtenerPorId(recetaDTO.getUsuarioId());
        Categoria categoria = categoriaService.obtenerPorId(recetaDTO.getCategoriaId());
        Receta receta = RecetaMapper.toEntity(recetaDTO, usuario, categoria);
        receta = recetaService.guardar(receta);
        return RecetaMapper.toDTO(receta);
    }

    // GET: buscar recetas con filtros
    @GetMapping
    public List<RecetaDTO> buscar(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String ingrediente,
            @RequestParam(required = false) String preferencia
    ) {
        List<Receta> recetas = recetaService.buscarRecetasConFiltros(categoria, ingrediente, preferencia);
        return recetas.stream()
                .map(RecetaMapper::toDTO)
                .collect(Collectors.toList());
    }

    // GET: obtener receta por ID
    @GetMapping("/{id}")
    public RecetaDTO obtener(@PathVariable int id) {
        Receta receta = recetaService.obtenerPorId(id);
        return RecetaMapper.toDTO(receta);
    }

    // POST: marcar receta como favorita
    @PostMapping("/{id}/favorito")
    public String marcarFavorito(
            @PathVariable("id") int recetaId,
            @RequestParam("usuarioId") int usuarioId
    ) {
        return meGustaService.marcarComoFavorita(usuarioId, recetaId);
    }
}
