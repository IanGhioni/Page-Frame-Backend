package ar.edu.unq.spring.exception;

public class ReviewSinValoracion extends RuntimeException {
    public ReviewSinValoracion() {
        super("No se puede dejar una review sin antes valorar");
    }
}
