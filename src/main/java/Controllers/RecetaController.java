package Controllers;

import java.util.List;
import model.Receta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import servicios.RecetaService;
import servicios.MeGustaService;


@RestController
@RequestMapping("/recetas")

public class RecetaController {

    @Autowired
    private RecetaService recetaService;

    @Autowired
    private MeGustaService meGustaService;


    //aGRega r3ceta POSTTT








}
