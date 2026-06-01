package com.alita.medscript.patient.dto;

import java.time.LocalDate;

import com.alita.medscript.patient.domain.Sexo;

import lombok.Builder;

@Builder
public record PacienteResponseDTO(
         Long id,
        String nome,
        String nomeSocial,
        LocalDate dataNascimento,
        Sexo sexo,
        String cpf,
        String email,
        ContatoDTO contato,
        EnderecoDTO endereco,
        DocumentoDTO documento,
        DadosPessoaisDTO dadosPessoais,
        DadosClinicosDTO dadosClinicos) {

}
