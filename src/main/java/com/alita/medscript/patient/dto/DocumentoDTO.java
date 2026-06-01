package com.alita.medscript.patient.dto;

import java.time.LocalDate;

public record DocumentoDTO(
        String rg,
        String orgaoEmissor,
        String ufEmissor,
        LocalDate dataExpedicao,
        String tipoDocumento,
        String numeroDocumento,
        String cartaoSus) {

}
