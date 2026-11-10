package com.alita.medscript.patient.dto;

import com.alita.medscript.patient.domain.enums.Sexo;

import java.time.LocalDate;

public record PacienteRequestDTO(
        String nomeCompleto,
        String cpf,
        LocalDate dataNascimento,
        String nomeMae,
        Sexo sexo,
        String telefoneContato,
        String contatoEmergenciaNome,
        String contatoEmergenciaTelefone,
        String alergias
) {
}
