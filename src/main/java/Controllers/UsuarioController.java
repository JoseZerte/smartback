package Controllers;

import model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import servicios.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/usuarios")


public class UsuarioController {

    @Autowired

    private UsuarioService usuarioService;

//POST

    @PostMapping

    public Usuario registrar(@RequestBody Usuario usuario){

        return usuarioService.guardar(usuario);
    }

    // obtener user por id GET

    @GetMapping("/{id}")

    public Usuario obtener(@PathVariable int id){return usuarioService.obtenerPorId(id);}


    // listar usuarios GET

    @GetMapping

    public List<Usuario> listar(){return usuarioService.listar();}

    // eliminar user DELETE

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id){usuarioService.eliminar(id);}



}
