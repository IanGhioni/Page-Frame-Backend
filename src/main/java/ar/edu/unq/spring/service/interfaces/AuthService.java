package ar.edu.unq.spring.service.interfaces;

import ar.edu.unq.spring.controller.dto.AuthResponseDTO;
import ar.edu.unq.spring.controller.dto.LoginRequestDTO;
import ar.edu.unq.spring.controller.dto.RegisterRequestDTO;

public interface AuthService {

    AuthResponseDTO login(LoginRequestDTO loginRequestDTO);
    AuthResponseDTO register(RegisterRequestDTO registerRequestDTO);
}
