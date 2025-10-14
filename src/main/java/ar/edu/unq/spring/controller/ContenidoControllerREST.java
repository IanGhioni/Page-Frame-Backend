package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.ContenidoBodyDTO;
import ar.edu.unq.spring.controller.dto.ContenidoResponseDTO;
import ar.edu.unq.spring.controller.dto.PageContenidoDTO;
import ar.edu.unq.spring.controller.dto.TextoReviewDTO;
import ar.edu.unq.spring.modelo.Contenido;
import ar.edu.unq.spring.exception.ContenidoNoEncontradoException;
import ar.edu.unq.spring.service.interfaces.ContenidoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contenido")
@CrossOrigin("http://localhost:5173")
public class ContenidoControllerREST {
    public final ContenidoService contenidoService;

    public ContenidoControllerREST(ContenidoService contenidoService) {
        this.contenidoService = contenidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContenidoResponseDTO createContenido(@RequestBody @Valid ContenidoBodyDTO contenidoDTO) {
        var contenido = contenidoService.crear(ContenidoBodyDTO.aModelo(contenidoDTO));
        return ContenidoResponseDTO.desdeModelo(contenido);
    }

    @GetMapping("/{id}")
    public ContenidoResponseDTO getContenidoById(@PathVariable Long id) {
        Contenido contenido = contenidoService.recuperar(id)
                .orElseThrow(ContenidoNoEncontradoException::new);
        return ContenidoResponseDTO.desdeModelo(contenido);
    }

    @GetMapping
    public List<ContenidoResponseDTO> getAllContenido() {
        return contenidoService.recuperarTodos().stream()
                .map(ContenidoResponseDTO::desdeModelo)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void deleteContenido(@PathVariable Long id) {
        var contenido = contenidoService.recuperar(id)
                .orElseThrow(ContenidoNoEncontradoException::new);
        contenidoService.eliminar(contenido);
    }

    @GetMapping("/search")
    public PageContenidoDTO searchContenido(@RequestParam String nombre, @RequestParam int nroPagina, @RequestParam int tamanioPagina) {
        Page<Contenido> p = contenidoService.recuperarPorNombre(nombre, nroPagina, tamanioPagina);
        PageContenidoDTO pDTO = PageContenidoDTO.converter(p);
        return pDTO;
    }

    @GetMapping("/explorarContenido")
    public PageContenidoDTO explorarContenido( @RequestParam int nroPagina, @RequestParam int tamanioPagina) {
        Page<Contenido> p = contenidoService.explorarContenidoPopular(nroPagina, tamanioPagina);
        PageContenidoDTO pDTO = PageContenidoDTO.converter(p);
        return pDTO;
    }

    @PostMapping("/{contenidoId}/valorar/{valoracion}/{usuarioId}")
    public void valorarContenido(@PathVariable Long contenidoId, @PathVariable Double valoracion, @PathVariable Long usuarioId) {
        contenidoService.valorarContenido(contenidoId, valoracion, usuarioId);
    }

    @PostMapping("/escribirReview/{contenidoId}/{usuarioId}")
    public void escribirReview(@PathVariable Long contenidoId, @PathVariable Long usuarioId, @RequestBody TextoReviewDTO textoDTO) {
        contenidoService.escribirReview(contenidoId, usuarioId, textoDTO.text());
    }


    @DeleteMapping("/{contenidoId}/eliminarValoracion/{usuarioId}")
    public void eliminarValoracion(@PathVariable Long contenidoId, @PathVariable Long usuarioId) {
        contenidoService.eliminarValoracionContenido(contenidoId, usuarioId);
    }

    @DeleteMapping("/{contenidoId}/eliminarReview/{usuarioId}")
    public void eliminarReview(@PathVariable Long contenidoId, @PathVariable Long usuarioId) {
        contenidoService.eliminarReview(contenidoId, usuarioId);
    }

    @GetMapping("/searchAutores")
    public PageContenidoDTO searchContenidoPorAutores(@RequestParam String nombre, @RequestParam int nroPagina, @RequestParam int tamanioPagina) {
        Page<Contenido> p = contenidoService.recuperarPorNombreDeAutores(nombre, nroPagina, tamanioPagina);
        PageContenidoDTO pDTO = PageContenidoDTO.converter(p);
        return pDTO;
    }

    @PutMapping("/escribirReview/{contenidoId}/{usuarioId}")
    public void editarTextoReview(@PathVariable Long contenidoId, @PathVariable Long usuarioId, @RequestBody TextoReviewDTO textoDTO) {
        contenidoService.editarTextoReview(contenidoId, usuarioId, textoDTO.text());
    }

    @GetMapping("/getReview/{contenidoId}/{usuarioId}")
    public TextoReviewDTO getTextoReview(@PathVariable Long contenidoId, @PathVariable Long usuarioId) {
        String texto = contenidoService.getTextoReview(contenidoId, usuarioId);
        return new TextoReviewDTO(texto);
    }
}
