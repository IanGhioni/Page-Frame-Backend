package ar.edu.unq.spring.controller.dto;


import lombok.*;

@ToString
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterRequestDTO {
    String username;
    String email;
    String password;
}
