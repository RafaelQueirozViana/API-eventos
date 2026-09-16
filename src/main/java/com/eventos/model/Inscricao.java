package com.eventos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "inscricao",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_inscricao_evento_participante",
                columnNames = {"evento_id", "participante_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O evento é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Evento evento;

    @NotNull(message = "O participante é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participante_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Participante participante;

    @Column(name = "data_inscricao", nullable = false)
    private LocalDateTime dataInscricao;

    @PrePersist
    public void aoSalvar() {
        this.dataInscricao = LocalDateTime.now();
    }
}