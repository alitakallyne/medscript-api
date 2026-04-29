package com.alita.medscript.paciente.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pacientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String nomeSocial;

    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Column(unique = true, nullable = false)
    private String cpf;

    private Boolean falecido;

    private LocalDate dataObito;

    private String email;

    @Embedded
    private Contato contato;

    @Embedded
    private Endereco endereco;

    @Embedded
    private Documento documento;

    @Embedded
    private DadosPessoais dadosPessoais;

    @Embedded
    private DadosClinicos dadosClinicos;
}
