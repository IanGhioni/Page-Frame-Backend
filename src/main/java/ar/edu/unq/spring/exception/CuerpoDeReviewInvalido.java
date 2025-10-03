package ar.edu.unq.spring.exception;

public class CuerpoDeReviewInvalido extends RuntimeException {
    public CuerpoDeReviewInvalido() {
        super("El texto de la review no puede ser vacio.");
    }
}
