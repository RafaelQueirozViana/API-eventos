package com.eventos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.eventos.model.Inscricao;

import java.util.List;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

    List<Inscricao> findByEventoId(Long eventoId);

    long countByEventoId(Long eventoId);

    boolean existsByEventoIdAndParticipanteId(Long eventoId, Long participanteId);
}
