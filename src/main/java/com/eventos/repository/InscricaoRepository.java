package com.eventos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.eventos.model.Inscricao;

@Repository
public interface InscricaoRepository  extends JpaRepository<Inscricao, Long> {
}
