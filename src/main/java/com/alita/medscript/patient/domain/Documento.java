package com.alita.medscript.patient.domain;

import java.time.LocalDate;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class Documento {

    private String rg;
    private String orgaoEmissor;
    private String ufEmissor;
    private LocalDate dataExpedicao;
    private String tipoDocumento;
    private String numeroDocumento;
    private String cartaoSus;
}
