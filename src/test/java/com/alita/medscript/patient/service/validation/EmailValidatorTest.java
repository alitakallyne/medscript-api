package com.alita.medscript.patient.service.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.alita.medscript.shared.exception.BusinessException;

public class EmailValidatorTest {

    private final EmailValidator validator = new EmailValidator();

    @Test
    @DisplayName("Deve validar email válido")
    void deveValidarEmailValido() {
        validator.validar("joao@email.com");
    }

    @Test
    @DisplayName("Deve lançar exceção quando email for nulo")
    void deveLancarExcecaoQuandoEmailNulo() {

        BusinessException exception =
                assertThrows(BusinessException.class,
                        () -> validator.validar(null));

        assertEquals("Email é obrigatório.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando email estiver em branco")
    void deveLancarExcecaoQuandoEmailEmBranco() {

        BusinessException exception =
                assertThrows(BusinessException.class,
                        () -> validator.validar(""));

        assertEquals("Email é obrigatório.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando email for inválido")
    void deveLancarExcecaoQuandoEmailInvalido() {

        BusinessException exception =
                assertThrows(BusinessException.class,
                        () -> validator.validar("email-invalido"));

        assertEquals("Email inválido.", exception.getMessage());
    }
}
