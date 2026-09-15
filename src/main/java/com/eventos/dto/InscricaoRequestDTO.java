package com.eventos.dto;

import jakarta.validation.constraints.NotNull;

public record InscricaoRequestDTO(

        @NotNull(message = "O id do evento é obrigatório")
        Long eventoId,

        @NotNull(message = "O id do participante é obrigatório")
        Long participanteId
) {
}
