package com.alita.medscript.clinical.service.validation;

import org.springframework.stereotype.Component;

import com.alita.medscript.clinical.repository.ClinicaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClinicaDuplicidadeValidator {

    private final ClinicaRepository repository;

    public void validar(String cnpj){

        repository.findByCnpj(cnpj);
    }
}
