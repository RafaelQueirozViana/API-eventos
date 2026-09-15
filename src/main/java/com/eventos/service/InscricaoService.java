package com.eventos.service;

import com.eventos.exception.EventoLotadoException;
import com.eventos.exception.InscricaoDuplicadaException;
import com.eventos.model.Inscricao;
import com.eventos.repository.InscricaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InscricaoService {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private EventoService eventoService;

    public Inscricao inscrever(Inscricao inscricao) {
        Long eventoId = inscricao.getEvento().getId();
        Long participanteId = inscricao.getParticipante().getId();

        if (eventoService.calcularVagasRestantes(eventoId) <= 0) {
            throw new EventoLotadoException("O evento está lotado.");
        }

        if (inscricaoRepository.existsByEventoIdAndParticipanteId(eventoId, participanteId)) {
            throw new InscricaoDuplicadaException(
                    "Participante já está inscrito neste evento."
            );
        }

        return inscricaoRepository.save(inscricao);
    }

    public void cancelarInscricao(Long id) {
        if (!inscricaoRepository.existsById(id)) {
            throw new IllegalArgumentException("Inscrição não encontrada.");
        }

        inscricaoRepository.deleteById(id);
    }
}