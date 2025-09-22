package ar.edu.unq.spring.modelo;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Setter
@Getter
@Entity
public class ContenidoDeUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Contenido contenido;

    private String estado;

    public ContenidoDeUsuario() {
    }

    public ContenidoDeUsuario( Usuario usuario, Contenido contenido, String estado) {
        this.usuario = usuario;
        this.contenido = contenido;
        this.estado = estado;
    }
}
