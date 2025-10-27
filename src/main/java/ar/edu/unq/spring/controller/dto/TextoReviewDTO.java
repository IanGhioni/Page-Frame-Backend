package ar.edu.unq.spring.controller.dto;

public record TextoReviewDTO(String text) {

    public String getText() {
        return text;
    }
}
