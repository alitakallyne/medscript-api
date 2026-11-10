package com.alita.medscript.patient.dto;

import com.alita.medscript.patient.domain.enums.StatusPaciente;

import java.time.LocalDate;

public record PacienteResumoDTO(
        Long id,
        String nomeCompleto,
        String cpf,
        LocalDate dataNascimento,
        StatusPaciente status
) {
}
