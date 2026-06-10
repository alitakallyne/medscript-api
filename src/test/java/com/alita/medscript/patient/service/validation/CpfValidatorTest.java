package com.alita.medscript.patient.service.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.alita.medscript.shared.exception.BusinessException;

public class CpfValidatorTest {

    private final CpfValidator validator = new CpfValidator();

    @Test
    @DisplayName("Deva validar CPF válido")
    void deveValidarCPFvalido(){
        validator.validar("52998224725");
    }

    @Test
    @DisplayName("Deve lançar uma Exceção qaundo o CPF for null")
    void deveLancarExcecaoQuandoCpfNulo(){

        BusinessException exception = 
            assertThrows(BusinessException.class,()-> validator.validar(null));

        assertEquals("CPF é obrigatório.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando CPF não possuir 11 digitos")
     void deveLancarExcecaoQuandoCpfInvalido() {

        BusinessException exception =
           assertThrows(BusinessException.class,()-> validator.validar("123"));

        assertEquals("CPF deve conter 11 dígitos.", exception.getMessage());
     }

     @Test
    @DisplayName("Deve lançar exceção quando todos os dígitos forem iguais")
    void deveLancarExcecaoQuandoTodosDigitosIguais() {

        BusinessException exception =
                assertThrows(BusinessException.class,
                        () -> validator.validar("11111111111"));

        assertEquals(
                "CPF inválido: todos os dígitos são iguais.",
                exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando dígitos verificadores forem inválidos")
    void deveLancarExcecaoQuandoDigitosInvalidos() {

        BusinessException exception =
                assertThrows(BusinessException.class,
                        () -> validator.validar("52998224724"));

        assertEquals(
                "CPF inválido: dígitos verificadores não conferem.",
                exception.getMessage());
    }
}
