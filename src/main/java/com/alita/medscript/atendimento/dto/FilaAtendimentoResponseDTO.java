package com.alita.medscript.atendimento.dto;

import java.time.LocalDateTime;

public record FilaAtendimentoResponseDTO(
        Long id,
        Long pacienteId,
        String nomePaciente,
        Prioridade prioridade,
        StatusFila status,
        LocalDateTime chegada
) {
    public static FilaAtendimentoResponseDTO de(FilaAtendimento f) {
        return new FilaAtendimentoResponseDTO(
                f.getId(),
                f.getPaciente().getId(),
                f.getPaciente().getNomeCompleto(),
                f.getPrioridade(),
                f.getStatus(),
                f.getChegada()
        );
    }
}
