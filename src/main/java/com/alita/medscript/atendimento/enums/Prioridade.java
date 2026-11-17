package com.alita.medscript.atendimento.enums;

public enum Prioridade {
    ESPECIAL(1), // maiores de 80 anos
    LEGAL(2),    // idosos 60+, gestantes, PcD e demais prioridades da lei
    NORMAL(3);

    private final int nivel;

    Prioridade(int nivel) { this.nivel = nivel; }

    public int getNivel() { return nivel; }
}