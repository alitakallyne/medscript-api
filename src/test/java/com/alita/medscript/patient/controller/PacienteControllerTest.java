package com.alita.medscript.patient.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.alita.medscript.patient.domain.Sexo;
import com.alita.medscript.patient.dto.ContatoDTO;
import com.alita.medscript.patient.dto.DadosPessoaisDTO;
import com.alita.medscript.patient.dto.DocumentoDTO;
import com.alita.medscript.patient.dto.EnderecoDTO;
import com.alita.medscript.patient.dto.PacienteRequestDTO;
import com.alita.medscript.patient.dto.PacienteResponseDTO;
import com.alita.medscript.patient.dto.PacienteResumoDTO;
import com.alita.medscript.patient.service.PacienteService;
import com.alita.medscript.shared.exception.BusinessException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PacienteController.class)
public class PacienteControllerTest {

    @MockBean
    private PacienteService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private PacienteRequestDTO uniquPacienteRequestDTO() {
        return new PacienteRequestDTO(
                "João Silva",
                "Joãozinho",
                LocalDate.of(1990, 5, 20),
                Sexo.MASCULINO,
                "123.456.789-00",
                "joao.silva@example.com",
                true,
                null, // LocalDate.now(),
                new ContatoDTO("086", "11987654321", "", ""),
                new EnderecoDTO("Rua A", "Bairro B", "Cidade C", "Estado D", "12345-678"),
                new DocumentoDTO("RG", "SSP", "SP", LocalDate.of(2010, 1, 15), "RG", "12.345.678-9", "123456789012345"),
                new DadosPessoaisDTO("Carlos Silva", "Maria Silva", "Ana Silva", 2, "Católica", "Casado",
                        "Ensino Médio Completo", "Branca", "Caucasiana", "Brasileira"));

    }

    @Test
    @DisplayName("Deve criar paciente com sucesso")
    void deveCriarPacienteComSucesso() throws Exception {

        PacienteRequestDTO request = uniquPacienteRequestDTO();
        PacienteResumoDTO response = new PacienteResumoDTO(
                1L,
                "João Silva",
                "123.456.789-00");

        when(service.criar(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/pacientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João Silva"));

        verify(service).criar(any());
    }

    @Test
    @DisplayName("Deve buscar paciente por ID")
    void deveBuscarPacientePorId() throws Exception {

        PacienteResponseDTO response = new PacienteResponseDTO(
                1L,
                "João Silva",
                "Joãozinho",
                LocalDate.of(1990, 5, 20),
                Sexo.MASCULINO,
                "123.456.789-00",
                "joao.silva@example.com",
                new ContatoDTO("086", "11987654321", "", ""),
                new EnderecoDTO("Rua A", "Bairro B", "Cidade C", "Estado D", "12345-678"),
                new DocumentoDTO("RG", "SSP", "SP", LocalDate.of(2010, 1, 15), "RG", "12.345.678-9", "123456789012345"),
                new DadosPessoaisDTO("Carlos Silva", "Maria Silva", "Ana Silva", 2, "Católica", "Casado",
                        "Ensino Médio Completo", "Branca", "Caucasiana", "Brasileira"));
        when(service.buscarPacientePorId(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/pacientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(service).buscarPacientePorId(1L);
    }

    @Test
    @DisplayName("Deve retornar erro ao criar paciente inválido")
    void deveRetornarErroCpfInvalido() throws Exception {

        when(service.criar(any()))
                .thenThrow(new BusinessException("CPF inválido"));

        mockMvc.perform(post("/api/v1/pacientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "nome": "João Silva",
                            "dataNascimento": "1990-05-20",
                            "cpf": "12345678900",
                            "email": "joao@email.com",
                            "falecido": false
                        }
                                  """))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("CPF inválido"));
    }
}
