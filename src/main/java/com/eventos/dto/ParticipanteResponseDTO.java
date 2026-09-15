package com.eventos.dto;

import com.eventos.model.Participante;

public record ParticipanteResponseDTO(
        Long id,
        String nome,
        String email
) {

    public static ParticipanteResponseDTO from(Participante participante) {
        return new ParticipanteResponseDTO(
                participante.getId(),
                participante.getNome(),
                participante.getEmail()
        );
    }
}
