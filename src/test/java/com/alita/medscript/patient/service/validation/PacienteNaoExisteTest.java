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
import com.alita.medscript.shared.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
public class PacienteNaoExisteTest {

     @Mock
    private PacienteRepository repository;

    @InjectMocks
    private PacienteNaoExiste validator;

    @Test
    @DisplayName("Deve validar paciente existente")
    void deveValidarPacienteExistente() {

        Paciente paciente = Paciente.builder().build();

        when(repository.findByCpf("123"))
                .thenReturn(Optional.of(paciente));

        validator.validar("123");

        verify(repository).findByCpf("123");
    }

    @Test
    @DisplayName("Deve lançar exceção quando paciente não existir")
    void deveLancarExcecaoQuandoPacienteNaoExiste() {

        when(repository.findByCpf("123"))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(ResourceNotFoundException.class,
                        () -> validator.validar("123"));

        assertEquals(
                "Paciente não encontrado",
                exception.getMessage());
    }
}
