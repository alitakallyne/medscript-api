package com.alita.medscript.patient.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.alita.medscript.patient.domain.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long>,
        JpaSpecificationExecutor<Paciente> {

    boolean existsByCpf(String cpf);

    boolean existsByNomeCompletoAndDataNascimentoAndNomeMae(
            String nomeCompleto, LocalDate dataNascimento, String nomeMae);

    boolean existsByNomeCompletoAndDataNascimentoAndNomeMaeAndCpfIsNull(
            String nomeCompleto, LocalDate dataNascimento, String nomeMae);

    Optional<Object> findByCpf(String cpf);
}