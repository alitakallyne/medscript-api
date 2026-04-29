package com.alita.medscript.paciente.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alita.medscript.paciente.domain.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente,Long>{

     Optional<Paciente> findByCpf(String cpf);

}
