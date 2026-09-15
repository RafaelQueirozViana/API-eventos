package com.eventos.service;

import com.eventos.exception.EventoNaoEncontradoException;
import com.eventos.model.Evento;
import com.eventos.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }

    public Evento buscarPorId(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new EventoNaoEncontradoException(
                        "Evento não encontrado."
                ));
    }

    public Evento salvar(Evento evento) {
        return eventoRepository.save(evento);
    }

    public int calcularVagasRestantes(Long eventoId) {
        Evento evento = buscarPorId(eventoId);

        int inscricoesAtivas = evento.getInscricoes().size();

        return evento.getCapacidadeMaxima() - inscricoesAtivas;
    }
}