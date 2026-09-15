package com.eventos.service;

import com.eventos.exception.EmailJaCadastradoException;
import com.eventos.exception.ParticipanteNaoEncontradoException;
import com.eventos.model.Participante;
import com.eventos.repository.ParticipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipanteService {

    @Autowired
    private ParticipanteRepository participanteRepository;

    public List<Participante> listarTodos() {
        return participanteRepository.findAll();
    }

    public Participante buscarPorId(Long id) {
        return participanteRepository.findById(id)
                .orElseThrow(() -> new ParticipanteNaoEncontradoException(
                        "Participante não encontrado."
                ));
    }

    public Participante salvar(Participante participante) {

        if (participanteRepository.existsByEmail(participante.getEmail())) {
            throw new EmailJaCadastradoException(
                    "Já existe um participante cadastrado com este e-mail."
            );
        }

        return participanteRepository.save(participante);
    }
}