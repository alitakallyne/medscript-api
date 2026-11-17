package com.alita.medscript.clinical.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.alita.medscript.clinical.domain.model.Clinica;

public interface ClinicaRepository extends JpaRepository<Clinica, Long>,
                JpaSpecificationExecutor<Clinica>{

     Optional<Clinica> findByCnpj(String cnpj);

}
