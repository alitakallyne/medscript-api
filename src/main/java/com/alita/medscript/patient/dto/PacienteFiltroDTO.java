package com.alita.medscript.patient.dto;

import com.alita.medscript.patient.domain.enums.StatusPaciente;

public record PacienteFiltroDTO(
        String nome,
        String cpf,
        StatusPaciente status
) {

}
