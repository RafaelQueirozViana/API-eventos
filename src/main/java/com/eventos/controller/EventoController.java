package com.eventos.controller;

import com.eventos.dto.EventoRequestDTO;
import com.eventos.dto.EventoResponseDTO;
import com.eventos.dto.ParticipanteResponseDTO;
import com.eventos.service.EventoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @PostMapping
    public ResponseEntity<EventoResponseDTO> cadastrar(@Valid @RequestBody EventoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.cadastrar(dto));
    }

    @GetMapping
    public List<EventoResponseDTO> listarTodos() {
        return eventoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.buscarPorId(id));
    }

    @GetMapping("/{id}/participantes")
    public ResponseEntity<List<ParticipanteResponseDTO>> listarParticipantes(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.listarParticipantes(id));
    }
}
