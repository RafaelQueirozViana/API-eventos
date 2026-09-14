package com.eventos.repository;

import com.eventos.model.Participante;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipanteRepository  extends JpaRepository<Participante, Long> {
    boolean existsByEmail(@NotBlank(message = "O e-mail é obrigatório") @Email(message = "O e-mail informado não é válido") String email);
}
