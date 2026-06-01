package com.alita.medscript.patient.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class DadosPessoais {

    private String nomePai;
    private String nomeMae;
    private String nomeConjuge;
    private Integer numeroFilhos;
    private String religiao;
    private String estadoCivil;
    private String escolaridade;
    private String cor;
    private String etnia;
    private String nacionalidade;
}
