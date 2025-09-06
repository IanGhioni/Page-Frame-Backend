package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Contenido;
import org.springframework.data.domain.Page;

import java.util.List;

public record PageContenidoDTO(List<Contenido> resultados,
                               int numeroDePagina,
                               int totalDePaginas,
                               int totalDeElementos) {

    public static PageContenidoDTO converter(Page<Contenido> page) {
        return new PageContenidoDTO(
                page.getContent(),
                page.getNumber(),
                page.getTotalPages(),
                (int) page.getTotalElements()
        );
    }
}
