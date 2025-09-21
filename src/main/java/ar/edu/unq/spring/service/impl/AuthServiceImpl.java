package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.controller.dto.AuthResponseDTO;
import ar.edu.unq.spring.controller.dto.LoginRequestDTO;
import ar.edu.unq.spring.controller.dto.RegisterRequestDTO;
import ar.edu.unq.spring.jwt.JWTRole;
import ar.edu.unq.spring.jwt.JwtService;
import ar.edu.unq.spring.modelo.Usuario;
import ar.edu.unq.spring.persistence.UsuarioDAO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthServiceImpl implements ar.edu.unq.spring.service.interfaces.AuthService {

    private final UsuarioDAO usuarioDAO;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;

    public AuthServiceImpl(UsuarioDAO usuarioDAO, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authManager) {
        this.usuarioDAO = usuarioDAO;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO loginRequest) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        UserDetails userDetails = usuarioDAO.findByUsername(loginRequest.getUsername()).orElseThrow();

        String token = jwtService.getToken(userDetails);

        return new AuthResponseDTO(token);
    }

    public static final Pattern emailRegex =
            Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = emailRegex.matcher(emailStr);
        return matcher.matches();
    }

    @Override
    public AuthResponseDTO register(RegisterRequestDTO registerRequest) {
        if (!validate(registerRequest.getEmail())) {
            throw new RuntimeException("El email es invalido");
        }

        Usuario user = new Usuario(
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword()),
                JWTRole.USER, registerRequest.getFotoPerfil());

        user = usuarioDAO.save(user);

        return new AuthResponseDTO(jwtService.getToken(user));
    }
}
