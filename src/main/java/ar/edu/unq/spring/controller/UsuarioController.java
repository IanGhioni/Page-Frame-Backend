package ar.edu.unq.spring.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
@CrossOrigin("http://localhost:5173")
public class UsuarioController {

    @GetMapping("/validarLogueo")
    public String sinTokenNoPasas() {
        return "Si llegaste aca el logueo fue exitoso!";
    }
}
