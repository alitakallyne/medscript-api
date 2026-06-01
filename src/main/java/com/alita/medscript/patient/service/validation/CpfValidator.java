package com.alita.medscript.patient.service.validation;


import org.springframework.stereotype.Component;

import com.alita.medscript.shared.exception.BusinessException;

@Component
public class CpfValidator {


    public void validar(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new BusinessException("CPF é obrigatório.");
        }

        String cpfLimpo = cpf.replaceAll("\\D", ""); // Remove caracteres não numéricos

        if (cpfLimpo.length() != 11) {
            throw new BusinessException("CPF deve conter 11 dígitos.");
        }

        if (cpfLimpo.chars().distinct().count() == 1) {
            throw new BusinessException("CPF inválido: todos os dígitos são iguais.");
        }

        if (!validarDigitosVerificadores(cpfLimpo)) {
            throw new BusinessException("CPF inválido: dígitos verificadores não conferem.");
        }
    }

    public boolean validarDigitosVerificadores(String cpf) {
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int digito1 = 11 - (soma % 11);
        if (digito1 > 9) {
            digito1 = 0;
        }

        soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        soma += digito1 * 2;
        int digito2 = 11 - (soma % 11);
        if (digito2 > 9) {
            digito2 = 0;
        }

        return Character.getNumericValue(cpf.charAt(9)) == digito1 && Character.getNumericValue(cpf.charAt(10)) == digito2;
    }
}
