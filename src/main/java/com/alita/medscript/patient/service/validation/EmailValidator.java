package com.alita.medscript.patient.service.validation;

import org.springframework.stereotype.Component;

import com.alita.medscript.shared.exception.BusinessException;

@Component
public class EmailValidator {


    public void validar(String email) {
        if (email == null || email.isBlank()) {
            throw new BusinessException("Email é obrigatório.");
        }

        String emailLimpo = email.trim();

        if (!emailLimpo.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new BusinessException("Email inválido.");
        }
    }
}
