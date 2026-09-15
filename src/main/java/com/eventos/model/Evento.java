package com.eventos.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "evento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do evento é obrigatório")
    @Column(nullable = false)
    private String nome;

    @Column(length = 2000)
    private String descricao;

    @NotNull(message = "A data do evento é obrigatória")
    @Column(nullable = false)
    private LocalDate data;

    @NotBlank(message = "O local do evento é obrigatório")
    @Column(nullable = false)
    private String local;

    @NotNull(message = "A capacidade máxima é obrigatória")
    @Positive(message = "A capacidade máxima deve ser maior que zero")
    @Column(name = "capacidade_maxima", nullable = false)
    private Integer capacidadeMaxima;


    @OneToMany(mappedBy = "evento")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Inscricao> inscricoes = new ArrayList<>();
}