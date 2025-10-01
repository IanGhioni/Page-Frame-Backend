package ar.edu.unq.spring.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    @NotBlank(message = "Debe proporcionar una URL de imagen")
    private String imagen;

    @Column(nullable = false)
    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @Column(nullable = false)
    @NotBlank(message = "El campo autores es obligatorio")
    private String autores;

    @Column(nullable = false)
    @Min(value = 1450)
    @Max(value = 2026)
    private int publicacion;

    @Column(nullable = false, length = 4096)
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public Contenido(String isbn, String imagen, String titulo, String autores, int publicacion, String descripcion,
            String categoria, double ratingCount, double ratingAverage, int largo) {
        this.isbn = isbn;
        this.imagen = imagen;
        this.titulo = titulo;
        this.autores = autores;
        this.publicacion = publicacion;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.ratingCount = ratingCount;
        this.ratingAverage = ratingAverage;
        this.largo = largo;
    }
    public void eliminarValoracion(Usuario usuario) {
        this.reviews.removeIf(review -> review.getUsuario().getId().equals(usuario.getId()));
        this.ratingCount-=1;
        this.actualizarRatingPromedio();
    }

    public void agregarOActualizarValoracion(Usuario usuario, Double valoracion) {
        boolean reviewExistente = this.reviews.stream()
                .filter(review -> review.getUsuario().getId().equals(usuario.getId()))
                .findFirst()
                .map(review -> {
                    review.setValoracion(valoracion); // Actualiza si existe
                    return true;
                })
                .orElseGet(() -> {
                    this.reviews.add(new Review(usuario, this, valoracion)); // Agrega si no existe
                    this.ratingCount += 1; // Incrementa solo si es nueva
                    return false;
                });

        this.actualizarRatingPromedio();
    }

    private void actualizarRatingPromedio() {
        if (this.ratingCount == 0) {
            this.ratingAverage = 0;
        } else {
            int cantReviewsUsuarios = this.reviews.size();

            double totalReviews = ratingAverage * (this.ratingCount - cantReviewsUsuarios); //externas
            totalReviews += this.reviews.stream().mapToDouble(Review::getValoracion).sum(); //le sumo las nuetras
            this.ratingAverage = totalReviews / this.ratingCount;
        }
    }

    public void agregarOActualizarReview(Usuario usuario, String texto) {
        this.reviews.stream()
                .filter(review -> review.getUsuario().getId().equals(usuario.getId()))
                .findFirst()
                .map(review -> {
                    review.setTexto(texto); // Actualiza si existe
                    return true;
                }).orElseThrow();
    }
}