package com.eventos.controller;

import com.eventos.model.Evento;
import com.eventos.model.Participante;
import com.eventos.repository.EventoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    private final EventoRepository eventoRepository;

    public EventoController(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @PostMapping
    public Evento cadastrar(@RequestBody Evento evento) {
        return eventoRepository.save(evento);
    }

    @GetMapping
    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Evento buscarPorId(@PathVariable Long id) {
        return eventoRepository.findById(id).orElse(null);
    }

    @GetMapping("/{id}/participantes")
    public List<Participante> listarParticipantes(@PathVariable Long id) {
        Evento evento = eventoRepository.findById(id).orElse(null);

        if (evento == null) {
            return List.of();
        }

        return evento.getInscricoes().stream()
                .map(inscricao -> inscricao.getParticipante())
                .toList();
    }
}