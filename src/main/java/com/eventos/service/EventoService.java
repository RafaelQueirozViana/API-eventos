package com.eventos.service;


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

}
