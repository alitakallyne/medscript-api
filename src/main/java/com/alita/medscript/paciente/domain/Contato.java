package com.alita.medscript.paciente.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class Contato {

    private String ddd;
    private String telefone;
    private String telefone2;
    private String telefone3;
}
