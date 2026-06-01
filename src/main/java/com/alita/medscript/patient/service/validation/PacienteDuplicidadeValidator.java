package com.alita.medscript.patient.service.validation;

import org.springframework.stereotype.Component;

import com.alita.medscript.patient.repository.PacienteRepository;
import com.alita.medscript.shared.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PacienteDuplicidadeValidator {
    private final PacienteRepository repository;

    public void validarCpfUnico(String cpf) {
        repository.findByCpf(cpf)
                .ifPresent(p -> {
                    throw new BusinessException("CPF já cadastrado");
                });
    }
}
