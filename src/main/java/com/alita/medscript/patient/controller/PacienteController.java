package com.alita.medscript.patient.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alita.medscript.patient.dto.PacienteFiltroDTO;
import com.alita.medscript.patient.dto.PacienteRequestDTO;
import com.alita.medscript.patient.dto.PacienteResponseDTO;
import com.alita.medscript.patient.dto.PacienteResumoDTO;
import com.alita.medscript.patient.service.PacienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pacientes")
public class PacienteController {

    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @PostMapping
    public PacienteResumoDTO criar(@RequestBody @Valid PacienteRequestDTO dto) {
        return service.criar(dto);
    }

    @GetMapping
    public ResponseEntity<Page<PacienteResumoDTO>> buscar(PacienteFiltroDTO filtro, Pageable pageable) {
        Page<PacienteResumoDTO> pacientes = service.buscar(filtro, pageable);
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> buscarPorId(@PathVariable Long id) {

        return ResponseEntity.ok(service.buscarPacientePorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid PacienteRequestDTO dto) {
        PacienteResponseDTO atualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.inativarPaciente(id);
        return ResponseEntity.noContent().build();
    }
}
