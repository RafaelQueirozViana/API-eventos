package com.eventos.service;


import com.eventos.model.Evento;
import com.eventos.model.Participante;
import com.eventos.repository.EventoRepository;
import com.eventos.repository.ParticipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipanteService {

    @Autowired
    private ParticipanteRepository participanteRepository;
    public List<Participante> listarTodos() {
        return participanteRepository.findAll();
    }

    public Optional<Participante> buscarPorId(Long id) {
        return participanteRepository.findById(id);
    }

    public Participante salvar(Participante participante) {
        return participanteRepository.save(participante);
    }
}
