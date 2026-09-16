package com.eventos.controller;

import com.eventos.dto.ParticipanteRequestDTO;
import com.eventos.dto.ParticipanteResponseDTO;
import com.eventos.service.ParticipanteService;
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
@RequestMapping("/participantes")
public class ParticipanteController {

    private final ParticipanteService participanteService;

    public ParticipanteController(ParticipanteService participanteService) {
        this.participanteService = participanteService;
    }

    @PostMapping
    public ResponseEntity<ParticipanteResponseDTO> cadastrar(@Valid @RequestBody ParticipanteRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(participanteService.cadastrar(dto));
    }

    @GetMapping
    public List<ParticipanteResponseDTO> listarTodos() {
        return participanteService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipanteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(participanteService.buscarPorId(id));
    }
}
