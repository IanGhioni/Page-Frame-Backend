package ar.edu.unq.spring.modelo;

import ar.edu.unq.spring.modelo.exception.MuchoPesoException;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
public class Personaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500, unique = true)
    private String nombre;
    private int vida;
    private int pesoMaximo;

    @OneToMany(
            mappedBy = "owner",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private Set<Item> inventario = new HashSet<>();

    public Personaje(String nombre, int vida, int pesoMaximo) {
        this.nombre = nombre;
        this.vida = vida;
        this.pesoMaximo = pesoMaximo;
    }

    public int getPesoActual() {
        return inventario.stream().mapToInt(Item::getPeso).sum();
    }

    public void recoger(Item item) {
        int pesoActual = getPesoActual();
        if (pesoActual + item.getPeso() > this.pesoMaximo) {
            throw new MuchoPesoException(this, item);
        }
        this.inventario.add(item);
        item.setOwner(this);
    }

}