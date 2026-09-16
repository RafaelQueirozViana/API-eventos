package com.eventos.service;

import com.eventos.dto.ParticipanteRequestDTO;
import com.eventos.dto.ParticipanteResponseDTO;
import com.eventos.exception.EmailJaCadastradoException;
import com.eventos.exception.ParticipanteNaoEncontradoException;
import com.eventos.model.Participante;
import com.eventos.repository.ParticipanteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipanteService {

    private final ParticipanteRepository participanteRepository;

    public ParticipanteService(ParticipanteRepository participanteRepository) {
        this.participanteRepository = participanteRepository;
    }

    // RF04 - Cadastrar um participante | RN03 - E-mail unico
    public ParticipanteResponseDTO cadastrar(ParticipanteRequestDTO dto) {
        if (participanteRepository.existsByEmail(dto.email())) {
            throw new EmailJaCadastradoException("Já existe um participante cadastrado com este e-mail");
        }

        Participante participante = new Participante();
        participante.setNome(dto.nome());
        participante.setEmail(dto.email());

        Participante salvo = participanteRepository.save(participante);
        return ParticipanteResponseDTO.from(salvo);
    }

    public List<ParticipanteResponseDTO> listarTodos() {
        return participanteRepository.findAll().stream()
                .map(ParticipanteResponseDTO::from)
                .toList();
    }

    public ParticipanteResponseDTO buscarPorId(Long id) {
        return ParticipanteResponseDTO.from(buscarEntidadeOuFalhar(id));
    }

    // Usado pelo InscricaoService para validar o participante de uma inscricao
    public Participante buscarEntidadeOuFalhar(Long id) {
        return participanteRepository.findById(id)
                .orElseThrow(() -> new ParticipanteNaoEncontradoException(
                        "Participante com id " + id + " não encontrado"));
    }
}
