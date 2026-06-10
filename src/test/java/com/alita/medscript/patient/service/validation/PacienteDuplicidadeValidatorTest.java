package com.alita.medscript.patient.service.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alita.medscript.patient.domain.Paciente;
import com.alita.medscript.patient.repository.PacienteRepository;
import com.alita.medscript.shared.exception.BusinessException;

@ExtendWith(MockitoExtension.class)
public class PacienteDuplicidadeValidatorTest {

    @Mock
    private PacienteRepository repository;

    @InjectMocks
    private PacienteDuplicidadeValidator validator;

    @Test
    @DisplayName("Deve permitir CPF único")
    void devePermitirCpfUnico() {

        when(repository.findByCpf("123"))
                .thenReturn(Optional.empty());

        validator.validarCpfUnico("123");

        verify(repository).findByCpf("123");
    }

    @Test
    @DisplayName("Deve lançar exceção quando CPF já existir")
    void deveLancarExcecaoQuandoCpfDuplicado() {

        Paciente paciente = Paciente.builder().build();

        when(repository.findByCpf("123"))
                .thenReturn(Optional.of(paciente));

        BusinessException exception =
                assertThrows(BusinessException.class,
                        () -> validator.validarCpfUnico("123"));

        assertEquals(
                "CPF já cadastrado",
                exception.getMessage());
    }
}
