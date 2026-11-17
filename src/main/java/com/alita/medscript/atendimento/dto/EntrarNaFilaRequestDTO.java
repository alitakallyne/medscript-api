package com.alita.medscript.atendimento.dto;

import jakarta.validation.constraints.NotNull;

public record EntrarNaFilaRequestDTO(
         @NotNull Long pacienteId,
        @NotNull Prioridade prioridade
) {

}
