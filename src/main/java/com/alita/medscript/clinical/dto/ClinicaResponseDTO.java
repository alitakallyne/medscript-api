package com.alita.medscript.clinical.dto;

public record ClinicaResponseDTO(
     Long id,

    String nomeFantasia,

    String cnpj,

    String email,

    Boolean ativa
) {

}
