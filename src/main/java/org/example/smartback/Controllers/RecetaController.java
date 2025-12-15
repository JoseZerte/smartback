package org.example.smartback.Controllers;

import org.example.smartback.DTOs.RecetaDTO;
import org.example.smartback.mapper.RecetaMapper;
import org.example.smartback.model.Categoria;
import org.example.smartback.model.Receta;
import org.example.smartback.model.Usuario;
import org.example.smartback.servicios.CategoriaService;
import org.example.smartback.servicios.MeGustaService;
import org.example.smartback.servicios.RecetaService;
import org.example.smartback.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
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

    @PostMapping
    public ResponseEntity<RecetaDTO> crear(@RequestBody RecetaDTO recetaDTO) {

        Usuario usuario = usuarioService.obtenerPorId(recetaDTO.getUsuarioId());
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Categoria categoria = categoriaService.obtenerPorId(recetaDTO.getCategoriaId());
        if (categoria == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        System.out.println("---- DEBUG CONTROLLER ----");
        if (recetaDTO.getIngredientes() != null) {
            System.out.println("Tamaño lista ingredientes: " + recetaDTO.getIngredientes().size());
        } else {
            System.out.println("ERROR: La lista de ingredientes es NULL");
        }
        System.out.println("--------------------------");

        Receta receta = RecetaMapper.toEntity(recetaDTO, usuario, categoria);

        Receta recetaGuardada = recetaService.crearRecetaConIngredientes(receta, recetaDTO.getIngredientes());

        return ResponseEntity.status(HttpStatus.CREATED).body(RecetaMapper.toDTO(recetaGuardada));
    }

    @GetMapping
    public ResponseEntity<List<RecetaDTO>> buscar(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String ingrediente,
            @RequestParam(required = false) String tipoDieta
    ) {
        List<Receta> recetas = recetaService.buscarRecetasConFiltros(categoria, ingrediente, tipoDieta);

        List<RecetaDTO> dtoList = recetas.stream()
                .map(RecetaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecetaDTO> obtenerPorId(@PathVariable int id) {
        Receta receta = recetaService.obtenerPorId(id);
        if (receta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(RecetaMapper.toDTO(receta));
    }

    @PostMapping("/{id}/favorito")
    public ResponseEntity<String> marcarFavorito(
            @PathVariable("id") int recetaId,
            @RequestParam("usuarioId") int usuarioId
    ) {
        String resultado = meGustaService.marcarComoFavorita(usuarioId, recetaId);
        return ResponseEntity.ok(resultado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecetaDTO> actualizar(@PathVariable int id, @RequestBody RecetaDTO recetaDTO) {
        Receta existente = recetaService.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Usuario usuario = usuarioService.obtenerPorId(recetaDTO.getUsuarioId());
        Categoria categoria = categoriaService.obtenerPorId(recetaDTO.getCategoriaId());

        if (usuario == null || categoria == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Receta actualizada = RecetaMapper.toEntity(recetaDTO, usuario, categoria);
        actualizada.setId(id);
        actualizada.setFecha_creacion(existente.getFecha_creacion());

        Receta resultado = recetaService.actualizar(actualizada);
        return ResponseEntity.ok(RecetaMapper.toDTO(resultado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        Receta existente = recetaService.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        recetaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{usuarioId}/favoritas")
    public ResponseEntity<List<RecetaDTO>> listarFavoritas(@PathVariable int usuarioId) {
        List<Receta> favoritas = meGustaService.obtenerRecetasFavoritasPorUsuario(usuarioId);

        if (favoritas.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }

        List<RecetaDTO> dtoList = favoritas.stream()
                .map(RecetaMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }
}