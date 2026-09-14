package com.eventos.controller;

import com.eventos.model.Participante;
import com.eventos.repository.ParticipanteRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/participantes")
public class ParticipanteController {

    private final ParticipanteRepository participanteRepository;

    public ParticipanteController(ParticipanteRepository participanteRepository) {
        this.participanteRepository = participanteRepository;
    }

    @PostMapping
    public ResponseEntity<Participante> cadastrar(@Valid @RequestBody Participante participante) {
        if (participanteRepository.existsByEmail(participante.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Já existe um participante cadastrado com este e-mail");
        }

        Participante salvo = participanteRepository.save(participante);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    public List<Participante> listarTodos() {
        return participanteRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Participante> buscarPorId(@PathVariable Long id) {
        Participante participante = participanteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Participante com id " + id + " não encontrado"));
        return ResponseEntity.ok(participante);
    }
}
