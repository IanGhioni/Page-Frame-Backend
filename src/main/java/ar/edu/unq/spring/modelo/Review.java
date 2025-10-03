package ar.edu.unq.spring.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

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

    private String texto;

    private LocalDate fecha;
    private LocalTime hora;

    public Review() {}

    public Review(Usuario usuario, Contenido contenido, Double valoracion) {
        this.usuario = usuario;
        this.contenido = contenido;
        this.valoracion = valoracion;
    }
}
