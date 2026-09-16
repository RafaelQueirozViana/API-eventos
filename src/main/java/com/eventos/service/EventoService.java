package com.eventos.service;

import com.eventos.dto.EventoRequestDTO;
import com.eventos.dto.EventoResponseDTO;
import com.eventos.dto.ParticipanteResponseDTO;
import com.eventos.exception.EventoNaoEncontradoException;
import com.eventos.model.Evento;
import com.eventos.repository.EventoRepository;
import com.eventos.repository.InscricaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final InscricaoRepository inscricaoRepository;

    public EventoService(EventoRepository eventoRepository, InscricaoRepository inscricaoRepository) {
        this.eventoRepository = eventoRepository;
        this.inscricaoRepository = inscricaoRepository;
    }

    // RF01 - Cadastrar um evento
    public EventoResponseDTO cadastrar(EventoRequestDTO dto) {
        Evento evento = new Evento();
        evento.setNome(dto.nome());
        evento.setDescricao(dto.descricao());
        evento.setData(dto.data());
        evento.setLocal(dto.local());
        evento.setCapacidadeMaxima(dto.capacidadeMaxima());

        Evento salvo = eventoRepository.save(evento);
        return EventoResponseDTO.from(salvo, 0);
    }

    // RF02 - Listar todos os eventos
    public List<EventoResponseDTO> listarTodos() {
        return eventoRepository.findAll().stream()
                .map(evento -> EventoResponseDTO.from(evento, inscricaoRepository.countByEventoId(evento.getId())))
                .toList();
    }

    // RF03 - Consultar um evento especifico, incluindo vagas restantes
    public EventoResponseDTO buscarPorId(Long id) {
        Evento evento = buscarEntidadeOuFalhar(id);
        return EventoResponseDTO.from(evento, inscricaoRepository.countByEventoId(id));
    }

    // RF06 - Listar os participantes inscritos em um evento
    public List<ParticipanteResponseDTO> listarParticipantes(Long id) {
        buscarEntidadeOuFalhar(id);

        return inscricaoRepository.findByEventoId(id).stream()
                .map(inscricao -> ParticipanteResponseDTO.from(inscricao.getParticipante()))
                .toList();
    }

    // Quantidade de vagas ainda disponiveis (RN01). Usa o repository (COUNT no banco)
    // em vez de evento.getInscricoes().size(): mais explicito e nao depende do
    // Open-Session-In-View para carregar a colecao lazy.
    public long vagasRestantes(Long eventoId) {
        Evento evento = buscarEntidadeOuFalhar(eventoId);
        long inscricoesAtivas = inscricaoRepository.countByEventoId(eventoId);
        return evento.getCapacidadeMaxima() - inscricoesAtivas;
    }

    // Usado pelo InscricaoService para validar o evento de uma inscricao
    public Evento buscarEntidadeOuFalhar(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new EventoNaoEncontradoException(
                        "Evento com id " + id + " não encontrado"));
    }
}
