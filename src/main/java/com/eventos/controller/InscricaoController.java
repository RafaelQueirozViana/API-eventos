package com.eventos.controller;

import com.eventos.model.Evento;
import com.eventos.model.Inscricao;
import com.eventos.model.Participante;
import com.eventos.repository.EventoRepository;
import com.eventos.repository.InscricaoRepository;
import com.eventos.repository.ParticipanteRepository;
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
    public ResponseEntity<Inscricao> inscrever(@RequestBody InscricaoRequest request) {
        Evento evento = eventoRepository.findById(request.getEventoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Evento com id " + request.getEventoId() + " não encontrado"));

        Participante participante = participanteRepository.findById(request.getParticipanteId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Participante com id " + request.getParticipanteId() + " não encontrado"));

        if (inscricaoRepository.existsByEventoIdAndParticipanteId(evento.getId(), participante.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Este participante já está inscrito neste evento");
        }

        long inscricoesAtivas = inscricaoRepository.countByEventoId(evento.getId());
        if (inscricoesAtivas >= evento.getCapacidadeMaxima()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "O evento está lotado, não há vagas disponíveis");
        }

        Inscricao inscricao = new Inscricao(evento, participante);
        Inscricao salva = inscricaoRepository.save(inscricao);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @GetMapping
    public List<Inscricao> listarTodas() {
        return inscricaoRepository.findAll();
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

    public static class InscricaoRequest {

        @NotNull(message = "O id do evento é obrigatório")
        private Long eventoId;

        @NotNull(message = "O id do participante é obrigatório")
        private Long participanteId;

        public InscricaoRequest() {
        }

        public Long getEventoId() {
            return eventoId;
        }

        public void setEventoId(Long eventoId) {
            this.eventoId = eventoId;
        }

        public Long getParticipanteId() {
            return participanteId;
        }

        public void setParticipanteId(Long participanteId) {
            this.participanteId = participanteId;
        }
    }
}
