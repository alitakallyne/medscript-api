package com.alita.medscript.patient.domain;

import java.time.LocalDate;

import com.alita.medscript.patient.domain.enums.Sexo;

import com.alita.medscript.patient.domain.enums.StatusPaciente;
import jakarta.persistence.Column;
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

    @Column(nullable = false, length = 200)
    private String nomeCompleto;

    @Column(unique = true, length = 11)
    private String cpf;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column(length = 200)
    private String nomeMae;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusPaciente status;

    @Column(length = 20)
    private String telefoneContato;

    @Column(length = 150)
    private String contatoEmergenciaNome;

    @Column(length = 20)
    private String contatoEmergenciaTelefone;

    @Column(length = 500)
    private String alergias;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paciente outro = (Paciente) o;
        return id != null && id.equals(outro.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}