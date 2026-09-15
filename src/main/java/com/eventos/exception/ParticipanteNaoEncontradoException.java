package com.eventos.exception;

public class ParticipanteNaoEncontradoException extends RuntimeException {

    public ParticipanteNaoEncontradoException(String message) {
        super(message);
    }
}