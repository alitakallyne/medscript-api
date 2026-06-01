package com.alita.medscript.patient.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.alita.medscript.patient.domain.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente,Long> ,
               JpaSpecificationExecutor<Paciente>{

     Optional<Paciente> findByCpf(String cpf);

}
