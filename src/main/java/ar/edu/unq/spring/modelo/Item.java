package ar.edu.unq.spring.modelo;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private int peso;

    @ManyToOne
    private Personaje owner;

    public Item(String nombre, int peso) {
        this.nombre = nombre;
        this.peso = peso;
    }

}