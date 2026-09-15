package com.eventos.controller;

import com.eventos.dto.EventoRequestDTO;
import com.eventos.dto.EventoResponseDTO;
import com.eventos.dto.ParticipanteResponseDTO;
import com.eventos.model.Evento;
import com.eventos.model.Participante;
import com.eventos.repository.EventoRepository;
import com.eventos.repository.InscricaoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    private final EventoRepository eventoRepository;
    private final InscricaoRepository inscricaoRepository;

    public EventoController(EventoRepository eventoRepository, InscricaoRepository inscricaoRepository) {
        this.eventoRepository = eventoRepository;
        this.inscricaoRepository = inscricaoRepository;
    }

    @PostMapping
    public ResponseEntity<EventoResponseDTO> cadastrar(@Valid @RequestBody EventoRequestDTO dto) {
        Evento evento = new Evento();
        evento.setNome(dto.nome());
        evento.setDescricao(dto.descricao());
        evento.setData(dto.data());
        evento.setLocal(dto.local());
        evento.setCapacidadeMaxima(dto.capacidadeMaxima());

        Evento salvo = eventoRepository.save(evento);
        return ResponseEntity.status(HttpStatus.CREATED).body(EventoResponseDTO.from(salvo, 0));
    }

    @GetMapping
    public List<EventoResponseDTO> listarTodos() {
        return eventoRepository.findAll().stream()
                .map(evento -> EventoResponseDTO.from(evento, inscricaoRepository.countByEventoId(evento.getId())))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> buscarPorId(@PathVariable Long id) {
        Evento evento = buscarEventoOuFalhar(id);
        long inscricoesAtivas = inscricaoRepository.countByEventoId(id);
        return ResponseEntity.ok(EventoResponseDTO.from(evento, inscricoesAtivas));
    }

    @GetMapping("/{id}/participantes")
    public ResponseEntity<List<ParticipanteResponseDTO>> listarParticipantes(@PathVariable Long id) {
        buscarEventoOuFalhar(id);

        List<ParticipanteResponseDTO> participantes = inscricaoRepository.findByEventoId(id).stream()
                .map(inscricao -> ParticipanteResponseDTO.from(inscricao.getParticipante()))
                .toList();

        return ResponseEntity.ok(participantes);
    }

    private Evento buscarEventoOuFalhar(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Evento com id " + id + " não encontrado"));
    }
}