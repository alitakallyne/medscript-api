package com.alita.medscript.patient.domain;

import java.time.LocalDate;

import com.alita.medscript.patient.domain.enums.StatusPaciente;

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
import lombok.Setter;

@Entity
@Table(name = "pacientes")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(length = 200)
    private String nomeSocial;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(nullable = false)
    @Builder.Default
    private Boolean falecido;

    @Column(nullable = true)
    private LocalDate dataObito;

    @Column(unique = true, length = 150)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPaciente status;

    @Embedded
    private Contato contato;

    @Embedded
    private Endereco endereco;

    @Embedded
    private Documento documento;

    @Embedded
    private DadosPessoais dadosPessoais;

   
}
