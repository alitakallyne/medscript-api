package com.alita.medscript.paciente.dto;

public record DadosPessoaisDTO(
        String nomePai,
        String nomeMae,
        String nomeConjuge,
        Integer numeroFilhos,
        String religiao,
        String estadoCivil,
        String escolaridade,
        String cor,
        String etnia,
        String nacionalidade) {

}
