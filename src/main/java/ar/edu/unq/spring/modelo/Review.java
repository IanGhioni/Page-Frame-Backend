package ar.edu.unq.spring.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Contenido contenido;

    private Double valoracion;

    public Review() {}

    public Review(Usuario usuario, Contenido contenido, Double valoracion) {
        this.usuario = usuario;
        this.contenido = contenido;
        this.valoracion = valoracion;
    }

}
