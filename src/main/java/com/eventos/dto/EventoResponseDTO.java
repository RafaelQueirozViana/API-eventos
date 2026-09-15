package com.eventos.dto;

import com.eventos.model.Evento;

import java.time.LocalDate;

public record EventoResponseDTO(
        Long id,
        String nome,
        String descricao,
        LocalDate data,
        String local,
        Integer capacidadeMaxima,
        Integer vagasDisponiveis
) {

    public static EventoResponseDTO from(Evento evento, long inscricoesAtivas) {
        int vagasDisponiveis = evento.getCapacidadeMaxima() - (int) inscricoesAtivas;
        return new EventoResponseDTO(
                evento.getId(),
                evento.getNome(),
                evento.getDescricao(),
                evento.getData(),
                evento.getLocal(),
                evento.getCapacidadeMaxima(),
                vagasDisponiveis
        );
    }
}
