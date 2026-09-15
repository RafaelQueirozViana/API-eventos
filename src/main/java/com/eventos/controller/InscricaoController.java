package com.eventos.controller;

import com.eventos.dto.InscricaoRequestDTO;
import com.eventos.dto.InscricaoResponseDTO;
import com.eventos.model.Evento;
import com.eventos.model.Inscricao;
import com.eventos.model.Participante;
import com.eventos.repository.EventoRepository;
import com.eventos.repository.InscricaoRepository;
import com.eventos.repository.ParticipanteRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/inscricoes")
public class InscricaoController {

    private final InscricaoRepository inscricaoRepository;
    private final EventoRepository eventoRepository;
    private final ParticipanteRepository participanteRepository;

    public InscricaoController(InscricaoRepository inscricaoRepository,
       EventoRepository eventoRepository,
       ParticipanteRepository participanteRepository) {
        this.inscricaoRepository = inscricaoRepository;
        this.eventoRepository = eventoRepository;
        this.participanteRepository = participanteRepository;
    }

    @PostMapping
    public ResponseEntity<InscricaoResponseDTO> inscrever(@Valid @RequestBody InscricaoRequestDTO dto) {
        Evento evento = eventoRepository.findById(dto.eventoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Evento com id " + dto.eventoId() + " não encontrado"));

        Participante participante = participanteRepository.findById(dto.participanteId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Participante com id " + dto.participanteId() + " não encontrado"));

        if (inscricaoRepository.existsByEventoIdAndParticipanteId(evento.getId(), participante.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Este participante já está inscrito neste evento");
        }

        long inscricoesAtivas = inscricaoRepository.countByEventoId(evento.getId());
        if (inscricoesAtivas >= evento.getCapacidadeMaxima()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "O evento está lotado, não há vagas disponíveis");
        }

        Inscricao inscricao = new Inscricao();
        inscricao.setEvento(evento);
        inscricao.setParticipante(participante);

        Inscricao salva = inscricaoRepository.save(inscricao);
        return ResponseEntity.status(HttpStatus.CREATED).body(InscricaoResponseDTO.from(salva));
    }

    @GetMapping
    public List<InscricaoResponseDTO> listarTodas() {
        return inscricaoRepository.findAll().stream()
                .map(InscricaoResponseDTO::from)
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        if (!inscricaoRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Inscrição com id " + id + " não encontrada");
        }
        inscricaoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
