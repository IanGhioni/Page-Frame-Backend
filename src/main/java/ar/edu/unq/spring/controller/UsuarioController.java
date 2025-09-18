package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.service.interfaces.UsuarioService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@CrossOrigin("http://localhost:5173")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/validarLogueo")
    public String sinTokenNoPasas() {
        return "Si llegaste aca el logueo fue exitoso!";
    }

    @PostMapping("/{idUser}/agregar/{idContenido}/aLista/{nombreLista}")
    public void agregarContenidoALista(@PathVariable Long idUser, @PathVariable Long idContenido, @PathVariable String nombreLista) {
        usuarioService.agregarContenidoAUsuario(idUser, idContenido, nombreLista);
    }


}
