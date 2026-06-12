package com.alita.medscript.patient.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.alita.medscript.patient.domain.Paciente;
import com.alita.medscript.patient.domain.enums.StatusPaciente;
import com.alita.medscript.patient.dto.PacienteFiltroDTO;
import com.alita.medscript.patient.repository.specification.PacienteSpecification;

@DataJpaTest
public class PacienteSpecificationTest {

    @Autowired
    private PacienteRepository repository;

    @BeforeEach
    void setup() {

        repository.deleteAll();

        repository.save(
                Paciente.builder()
                        .nome("João Silva")
                        .cpf("12345678909")
                        .status(StatusPaciente.ATIVO)
                        .dataNascimento(LocalDate.of(1990, 5, 20))
                        .falecido(false)
                        .dataObito(null)
                        .build());

        repository.save(
                Paciente.builder()
                        .nome("Maria Souza")
                        .cpf("98765432100")
                        .status(StatusPaciente.INATIVO)
                        .dataNascimento(LocalDate.of(1998, 9, 20))
                        .falecido(false)
                        .dataObito(null)
                        .build());
    }

    @Test
    @DisplayName("Deve buscar por nome com sucesso")
    void deveBuscarPorNome() {
        var filtro = new PacienteFiltroDTO("Maria Souza", null, null);

        var resultado = repository.findAll(
                PacienteSpecification.comFiltro(filtro));

        assertEquals(1, resultado.size());
        assertEquals("Maria Souza", resultado.get(0).getNome());
    }

    @Test
    @DisplayName("Deve buscar por CPF com sucesso")
    void deveBuscarPorCPF() {
        var filtro = new PacienteFiltroDTO(null, "98765432100", null);

        var resultado = repository.findAll(
                PacienteSpecification.comFiltro(filtro));

        assertEquals(1, resultado.size());
        assertEquals("98765432100", resultado.get(0).getCpf());
    }

    @Test
    @DisplayName("Deve buscar por status")
    void deveBuscarPorStatus() {

        var filtro = new PacienteFiltroDTO(
                null,
                null,
                StatusPaciente.ATIVO);

        var resultado = repository.findAll(
                PacienteSpecification.comFiltro(filtro));

        assertEquals(1, resultado.size());
        assertEquals(
                StatusPaciente.ATIVO,
                resultado.get(0).getStatus());
    }

     @Test
    @DisplayName("Deve Retornar Todos Quando Nao Informar Filtros")
    void deveRetornarTodosQuandoNaoInformarFiltros() {

        var filtro = new PacienteFiltroDTO(
                null,
                null,
                null);

        var resultado = repository.findAll(
                PacienteSpecification.comFiltro(filtro));

        assertEquals(2, resultado.size());
    }
}
