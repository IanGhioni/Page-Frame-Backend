package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.*;
import ar.edu.unq.spring.modelo.Contenido;
import ar.edu.unq.spring.modelo.ContenidoDeUsuarioPersonalizado;
import ar.edu.unq.spring.service.interfaces.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @GetMapping("/{username}")
    public UsuarioResponseDTO buscarUsuarioPorUsername(@PathVariable String username) {
        var usuario = usuarioService.recuperarPorUsername(username);
        return UsuarioResponseDTO.fromModel(usuario);
    }

    @GetMapping("/id/{id}")
    public UsuarioResponseDTO buscarUsuarioPorId(@PathVariable Long id) {
        var usuario = usuarioService.recuperar(id);
        return UsuarioResponseDTO.fromModel(usuario);
    }

    @PostMapping("/{idUser}/agregar/{idContenido}/aLista/{nombreLista}")
    public void agregarContenidoALista(@PathVariable Long idUser, @PathVariable Long idContenido, @PathVariable String nombreLista) {
        usuarioService.agregarContenidoAUsuario(idUser, idContenido, nombreLista);
    }

    @DeleteMapping("/{idUser}/eliminarDeLista/{idContenido}")
    public void eliminarContenidoDeLista(@PathVariable Long idUser, @PathVariable Long idContenido) {
        usuarioService.eliminarContenidoDeUsuario(idUser, idContenido);
    }

    @GetMapping("/{idUser}/lista/{nombreLista}")
    public List<ContenidoDeUsuarioResponseDTO> listarContenidoLista(@PathVariable Long idUser, @PathVariable String nombreLista) {
        return usuarioService.getContenidosDeUsuarioConEstado(idUser, nombreLista).stream()
                .map(ContenidoDeUsuarioResponseDTO::fromModel)
                .collect(Collectors.toList());
    }

    @PostMapping("/{idUser}/crearLista")
    public void crearListaPersonalizada(@RequestBody ListaPersonalizadaDTO lista, @PathVariable Long idUser) {
        usuarioService.crearListaPersonalizada(idUser, lista.nombre(), lista.descripcion());
    }

    @PostMapping("/{idUser}/getListaPersonalizada/{nombreLista}")
    public ContenidoDeUsuarioPersonalizado getListaPersonalizada(@PathVariable Long idUser, @PathVariable String nombreLista) {
        return usuarioService.getListaPersonalizada(idUser, nombreLista);
    }

    @PostMapping("/{idUser}/agregar/{idContenido}/aListaPersonalizada/{nombreLista}")
    public void agregarContenidoAListaPersonalizada(@PathVariable Long idUser, @PathVariable Long idContenido, @PathVariable String nombreLista) {
        usuarioService.agregarContenidoAListaPersonalizada(idUser, idContenido, nombreLista);
    }

    @GetMapping("/{idUser}/listas")
    public List<ListaPersonalizadaDTO> getListasPersonalizadasDeUsuario(@PathVariable Long idUser) {
        var listas = usuarioService.getListasPersonalizadasDeUsuario(idUser);

        return listas.stream()
                .map(lista -> ListaPersonalizadaDTO.fromModel(
                        lista.getContenido().stream()
                                .map(ContenidoResponseDTO::desdeModelo)
                                .collect(Collectors.toSet()),
                        lista.getNombre(),
                        lista.getDescripcion()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{idUser}/getContenidoDeListaPersonalizada/{nombreLista}")
    public Set<ContenidoResponseDTO> getContenidosDeListaPersonalizada(@PathVariable Long idUser, @PathVariable String nombreLista) {
        return usuarioService.getContenidosDeListaPersonalizada(idUser, nombreLista).stream()
                .map(ContenidoResponseDTO::desdeModelo)
                .collect(Collectors.toSet());
    }

    @DeleteMapping("/{idUser}/eliminar/{contenidoId}/DeListaPersonalizada/{nombre}")
    public void eliminarContenidoDeListaPersonalizada(@PathVariable Long idUser, @PathVariable Long contenidoId, @PathVariable String nombre) {
        usuarioService.eliminarContenidoDeListaPersonalizada(idUser, contenidoId, nombre);
    }

    @DeleteMapping("/{idUser}/eliminar/listaPersonalizada/{nombre}")
    public void eliminarListaPersonalizada(@PathVariable Long idUser, @PathVariable String nombre) {
        usuarioService.eliminarListaPersonalizada(idUser, nombre);
    }

}
