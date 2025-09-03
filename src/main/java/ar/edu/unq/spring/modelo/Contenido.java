package ar.edu.unq.spring.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@ToString
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
public class Contenido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^(\\d{9}[\\dX]|\\d{13})$", message = "ISBN inválido")
    private String isbn;

    @Pattern(
            regexp = "^(https?|ftp)://.*$",
            message = "La imagen debe ser una URL válida"
    )
    private String imagen;

    @Column(nullable = false)
    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @Column(nullable = false)
    private int publicacion;

    @Column(nullable = false)
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @Column(nullable = false)
    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;

    @PositiveOrZero
    private double ratingCount;

    @PositiveOrZero
    private double ratingAverage;

    @Positive
    @Column(nullable = false)
    private int largo;

    public Contenido(String isbn, String imagen, String titulo, int publicacion, String descripcion,
            String categoria, double ratingCount, double ratingAverage, int largo) {
        this.isbn = isbn;
        this.imagen = imagen;
        this.titulo = titulo;
        this.publicacion = publicacion;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.ratingCount = ratingCount;
        this.ratingAverage = ratingAverage;
        this.largo = largo;
    }
}