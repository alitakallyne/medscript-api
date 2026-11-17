package com.alita.medscript.patient.service.validation;

import org.springframework.stereotype.Component;


import com.alita.medscript.patient.repository.PacienteRepository;
import com.alita.medscript.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;



@Component
@RequiredArgsConstructor
public class PacienteNaoExiste {

    private final PacienteRepository repository;

    public void validar(String cpf) {
        repository.findByCpf(cpf)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Paciente não encontrado"));

    }
}
