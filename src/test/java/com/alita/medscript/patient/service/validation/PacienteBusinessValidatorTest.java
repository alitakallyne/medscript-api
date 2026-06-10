package com.alita.medscript.patient.service.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.alita.medscript.patient.domain.Paciente;
import com.alita.medscript.shared.exception.BusinessException;

public class PacienteBusinessValidatorTest {

    private final PacienteBusinessValidator validator =
            new PacienteBusinessValidator();

    private Paciente paciente;

    @BeforeEach
    void setup() {
        paciente = Paciente.builder()
                .nome("João")
                .falecido(false)
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .build();
    }

    @Test
    @DisplayName("Deve validar paciente válido")
    void deveValidarPacienteValido() {
        validator.validar(paciente);
    }

    @Test
    @DisplayName("Deve lançar exceção quando data nascimento for futura")
    void deveLancarExcecaoDataNascimentoFutura() {

        paciente.setDataNascimento(
                LocalDate.now().plusDays(1));

        BusinessException exception =
                assertThrows(BusinessException.class,
                        () -> validator.validar(paciente));

        assertEquals(
                "Data de nascimento não pode ser futura.",
                exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando paciente falecido não possuir data óbito")
    void deveLancarExcecaoSemDataObito() {

        paciente.setFalecido(true);
        paciente.setDataObito(null);

        BusinessException exception =
                assertThrows(BusinessException.class,
                        () -> validator.validar(paciente));

        assertEquals(
                "Data de óbito é obrigatória para pacientes falecidos.",
                exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando paciente vivo possuir data óbito")
    void deveLancarExcecaoPacienteVivoComDataObito() {

        paciente.setFalecido(false);
        paciente.setDataObito(LocalDate.now());

        BusinessException exception =
                assertThrows(BusinessException.class,
                        () -> validator.validar(paciente));

        assertEquals(
                "Data de óbito deve ser nula para pacientes vivos.",
                exception.getMessage());
    }
}
