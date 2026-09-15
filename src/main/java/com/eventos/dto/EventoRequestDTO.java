package com.eventos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record EventoRequestDTO(

        @NotBlank(message = "O nome do evento é obrigatório")
        String nome,

        String descricao,

        @NotNull(message = "A data do evento é obrigatória")
        LocalDate data,

        @NotBlank(message = "O local do evento é obrigatório")
        String local,

        @NotNull(message = "A capacidade máxima é obrigatória")
        @Positive(message = "A capacidade máxima deve ser maior que zero")
        Integer capacidadeMaxima
) {
}
