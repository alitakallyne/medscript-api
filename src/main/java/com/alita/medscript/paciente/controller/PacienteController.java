package com.alita.medscript.paciente.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alita.medscript.paciente.dto.PacienteRequestDTO;
import com.alita.medscript.paciente.dto.PacienteResumoDTO;
import com.alita.medscript.paciente.service.PacienteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("paciente/v1")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService service;

    @PostMapping
    public PacienteResumoDTO criar(@RequestBody @Valid PacienteRequestDTO dto) {
        return service.criar(dto);
    }

}
