package com.eventos.controller;

import com.eventos.dto.InscricaoRequestDTO;
import com.eventos.dto.InscricaoResponseDTO;
import com.eventos.service.InscricaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/inscricoes")
public class InscricaoController {

    private final InscricaoService inscricaoService;

    public InscricaoController(InscricaoService inscricaoService) {
        this.inscricaoService = inscricaoService;
    }

    @PostMapping
    public ResponseEntity<InscricaoResponseDTO> inscrever(@Valid @RequestBody InscricaoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inscricaoService.inscrever(dto));
    }

    @GetMapping
    public List<InscricaoResponseDTO> listarTodas() {
        return inscricaoService.listarTodas();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        inscricaoService.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}
