package org.example.smartback.servicios;

import org.example.smartback.model.Ingrediente;
import org.example.smartback.model.MeGusta;
import org.example.smartback.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.smartback.repository.IngredienteRepository;
import org.example.smartback.repository.MeGustaRepository;
import org.example.smartback.repository.UsuarioRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EstadisticasService {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private MeGustaRepository meGustaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // 1️⃣ Top 5 ingredientes más usados
    public List<Map<String, Object>> top5Ingredientes() {
        // contamos cuántas recetas usan cada ingrediente
        List<Ingrediente> todos = ingredienteRepository.findAll();

        List<Map<String, Object>> resultado = todos.stream()
                .map(ing -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("ingrediente", ing.getNombre());
                    map.put("recetasUsadas", ing.getRecetas().size());
                    return map;
                })
                .sorted((a, b) -> ((Integer) b.get("recetasUsadas")).compareTo((Integer) a.get("recetasUsadas")))
                .limit(5)
                .collect(Collectors.toList());

        return resultado;
    }

    // 2️⃣ Usuario con receta más veces marcada como favorita
    public Map<String, Object> usuarioPopular() {
        List<MeGusta> todosLikes = meGustaRepository.findAll();

        // contar likes por usuario
        Map<Usuario, Long> contador = todosLikes.stream()
                .collect(Collectors.groupingBy(MeGusta::getUsuario, Collectors.counting()));

        // encontrar usuario con más likes
        Usuario topUsuario = contador.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        Map<String, Object> resultado = new HashMap<>();
        if (topUsuario != null) {
            resultado.put("usuario", topUsuario.getNombre());
            resultado.put("likesTotales", contador.get(topUsuario));
        }

        return resultado;
    }
}

