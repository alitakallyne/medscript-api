package com.alita.medscript.patient.service.validation;

import org.springframework.stereotype.Component;

import com.alita.medscript.patient.domain.Paciente;
import com.alita.medscript.patient.repository.PacienteRepository;
import com.alita.medscript.shared.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PacienteDuplicidadeValidator {

    private final PacienteRepository repository;

    public void validar(Paciente paciente) {

        boolean temCpf = paciente.getCpf() != null && !paciente.getCpf().isBlank();

        // caminho (a): só consulta CPF se o paciente trouxe CPF
        if (temCpf && repository.existsByCpf(paciente.getCpf())) {
            throw new BusinessException("Já existe um paciente cadastrado com este CPF.");
        }

        // caminho (b): nome + data de nascimento + mãe
        boolean mesmaPessoa = repository.existsByNomeCompletoAndDataNascimentoAndNomeMae(
                paciente.getNomeCompleto(),
                paciente.getDataNascimento(),
                paciente.getNomeMae());

        if (mesmaPessoa) {
            throw new BusinessException(
                    "Já existe um paciente cadastrado com este nome, data de nascimento e nome da mãe.");
        }
    }
}