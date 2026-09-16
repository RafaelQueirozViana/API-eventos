package com.eventos.service;

import com.eventos.dto.InscricaoRequestDTO;
import com.eventos.dto.InscricaoResponseDTO;
import com.eventos.exception.EventoLotadoException;
import com.eventos.exception.InscricaoDuplicadaException;
import com.eventos.exception.InscricaoNaoEncontradaException;
import com.eventos.model.Evento;
import com.eventos.model.Inscricao;
import com.eventos.model.Participante;
import com.eventos.repository.InscricaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InscricaoService {

    private final InscricaoRepository inscricaoRepository;
    private final EventoService eventoService;
    private final ParticipanteService participanteService;

    public InscricaoService(InscricaoRepository inscricaoRepository,
                             EventoService eventoService,
                             ParticipanteService participanteService) {
        this.inscricaoRepository = inscricaoRepository;
        this.eventoService = eventoService;
        this.participanteService = participanteService;
    }

    // RF05 - Inscrever um participante em um evento | RN01, RN02
    public InscricaoResponseDTO inscrever(InscricaoRequestDTO dto) {
        // Antes so o evento era validado (indiretamente); um participanteId
        // inexistente ia direto pro banco e estourava erro de FK (500) em vez de 404.
        Evento evento = eventoService.buscarEntidadeOuFalhar(dto.eventoId());
        Participante participante = participanteService.buscarEntidadeOuFalhar(dto.participanteId());

        // RN02 - nao permite inscricao duplicada no mesmo evento
        if (inscricaoRepository.existsByEventoIdAndParticipanteId(evento.getId(), participante.getId())) {
            throw new InscricaoDuplicadaException("Este participante já está inscrito neste evento");
        }

        // RN01 - so permite inscricao se ainda houver vagas
        long inscricoesAtivas = inscricaoRepository.countByEventoId(evento.getId());
        if (inscricoesAtivas >= evento.getCapacidadeMaxima()) {
            throw new EventoLotadoException("O evento está lotado, não há vagas disponíveis");
        }

        Inscricao inscricao = new Inscricao();
        inscricao.setEvento(evento);
        inscricao.setParticipante(participante);

        Inscricao salva = inscricaoRepository.save(inscricao);
        return InscricaoResponseDTO.from(salva);
    }

    public List<InscricaoResponseDTO> listarTodas() {
        return inscricaoRepository.findAll().stream()
                .map(InscricaoResponseDTO::from)
                .toList();
    }

    // RF07 - Cancelar a inscricao | RN04 - libera a vaga (o calculo de vagasDisponiveis usa
    // a contagem atual de inscricoes, entao remover o registro ja libera a vaga)
    public void cancelar(Long id) {
        if (!inscricaoRepository.existsById(id)) {
            throw new InscricaoNaoEncontradaException("Inscrição com id " + id + " não encontrada");
        }
        inscricaoRepository.deleteById(id);
    }
}
