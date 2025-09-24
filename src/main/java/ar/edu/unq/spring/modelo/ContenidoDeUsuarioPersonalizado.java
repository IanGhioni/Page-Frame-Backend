package ar.edu.unq.spring.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@ToString
@Setter
@Getter
@Entity
public class ContenidoDeUsuarioPersonalizado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Contenido> contenido;

    private String nombre;

    private String descripcion;

    public ContenidoDeUsuarioPersonalizado() {
    }

    public ContenidoDeUsuarioPersonalizado(Usuario usuario, String nombre, String descripcion, Set<Contenido> contenido) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.contenido = contenido;
    }
}

