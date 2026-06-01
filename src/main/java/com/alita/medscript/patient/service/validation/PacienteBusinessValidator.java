package com.alita.medscript.patient.service.validation;

import org.springframework.stereotype.Component;

import com.alita.medscript.patient.domain.Paciente;
import com.alita.medscript.shared.exception.BusinessException;

@Component
public class PacienteBusinessValidator {

    public void validar(Paciente paciente) {
        validarDataNascimento(paciente);
        validarObito(paciente);
    }

    public void validarObito(Paciente paciente) {
        if (paciente.getFalecido() && paciente.getDataObito() == null) {
            throw new BusinessException("Data de óbito é obrigatória para pacientes falecidos.");
        }
        if (!paciente.getFalecido() && paciente.getDataObito() != null) {
            throw new BusinessException("Data de óbito deve ser nula para pacientes vivos.");
        }
    }

    public void validarDataNascimento(Paciente paciente) {
        if (paciente.getDataNascimento().isAfter(java.time.LocalDate.now())) {
            throw new BusinessException("Data de nascimento não pode ser futura.");
        }
    }
}
