package com.eventos.dto;

import com.eventos.model.Inscricao;

import java.time.LocalDateTime;

public record InscricaoResponseDTO(
        Long id,
        Long eventoId,
        String eventoNome,
        Long participanteId,
        String participanteNome,
        LocalDateTime dataInscricao
) {

    public static InscricaoResponseDTO from(Inscricao inscricao) {
        return new InscricaoResponseDTO(
                inscricao.getId(),
                inscricao.getEvento().getId(),
                inscricao.getEvento().getNome(),
                inscricao.getParticipante().getId(),
                inscricao.getParticipante().getNome(),
                inscricao.getDataInscricao()
        );
    }
}
