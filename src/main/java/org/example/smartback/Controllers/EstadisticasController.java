package org.example.smartback.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.example.smartback.servicios.EstadisticasService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/estadisticas")
public class EstadisticasController {

    @Autowired
    private EstadisticasService estadisticasService;

    // 1️⃣ Top 5 ingredientes
    @GetMapping("/ingredientes")
    public List<Map<String, Object>> topIngredientes() {
        return estadisticasService.top5Ingredientes();
    }

    // 2️⃣ Usuario con receta más guardada
    @GetMapping("/usuarioPopular")
    public Map<String, Object> usuarioPopular() {
        return estadisticasService.usuarioPopular();
    }
}
