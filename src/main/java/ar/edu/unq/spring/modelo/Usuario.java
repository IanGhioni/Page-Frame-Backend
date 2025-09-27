package ar.edu.unq.spring.modelo;

import ar.edu.unq.spring.jwt.JWTRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@ToString
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<ContenidoDeUsuario> misContenidos = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<ContenidoDeUsuarioPersonalizado> listasPersonalizadas;

    private String fotoPerfil;

    private JWTRole rol;

        public Usuario(String username, String email, String password, JWTRole rol, String fotoPerfil) {
            this.username = username;
            this.email = email;
            this.password = password;
            this.rol = rol;
            this.fotoPerfil = fotoPerfil;
            this.listasPersonalizadas = new HashSet<>();
        }


    public void agregarContenido(Contenido contenido, String estado) {
        ContenidoDeUsuario nuevo = new ContenidoDeUsuario(this, contenido, estado);
        this.misContenidos.add(nuevo);
    }

    public void actualizarContenido(Contenido contenido, String estado) {
        for (ContenidoDeUsuario cdu : this.misContenidos) {
            if (cdu.getContenido().getId().equals(contenido.getId())) {
                cdu.setEstado(estado);
                return;
            }
        }
    }

    public void eliminarContenido(Contenido contenido) {
        this.misContenidos.removeIf(cdu -> cdu.getContenido().getId().equals(contenido.getId()));
    }

    public void agregarListaPersonalizada(String nombre, String descripcion) {
        ContenidoDeUsuarioPersonalizado nuevaLista = new ContenidoDeUsuarioPersonalizado(this, nombre, descripcion, new HashSet<>());
        this.listasPersonalizadas.add(nuevaLista);
    }

    public void agregarContenidoAListaPersonalizada(ContenidoDeUsuarioPersonalizado lista, Contenido contenido) {
            lista.getContenido().add(contenido);
    }

    public void eliminarContenidoDeLista(Contenido contenido, Set<Contenido> contenidoDeUsuarioPersonalizado) {
            contenidoDeUsuarioPersonalizado.remove(contenido);
    }

    public void eliminarListaPersonalizada(String nombre) {
            ContenidoDeUsuarioPersonalizado lista = this.listasPersonalizadas.stream().filter(cdup -> cdup.getNombre().equals(nombre)).findFirst().orElse(null);
            listasPersonalizadas.remove(lista);
    }

    public boolean yaExisteListaDeNombre(String nombre) {
        ContenidoDeUsuarioPersonalizado lista = this.listasPersonalizadas.stream().filter(cdup -> cdup.getNombre().equals(nombre)).findFirst().orElse(null);
        return (lista != null);
    }

    //Metodos de la interfaz UserDetailes aca debajo (No tocar por favorcito)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol.toString()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
