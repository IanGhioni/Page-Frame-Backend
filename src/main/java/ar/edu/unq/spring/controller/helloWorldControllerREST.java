package ar.edu.unq.spring.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/holaMundo")
public class helloWorldControllerREST {

    @GetMapping
    public String helloWorld() {
        return "Hola Mundo";
    }
}
