package com.alita.medscript.patient.dto;

import java.time.LocalDate;

import com.alita.medscript.patient.domain.Sexo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PacienteRequestDTO(
        @NotBlank(message = "Nome é obrigatório") String nome,
        String nomeSocial,
        
        @NotNull(message = "Data de Nascimento é obrigatória")
        LocalDate dataNascimento,
        Sexo sexo,
        @NotBlank(message = "CPF é obrigatório") String cpf,
        @Email(message = "Email inválido") String email,

        Boolean falecido,
        LocalDate dataObito,
        ContatoDTO contato,
        EnderecoDTO endereco,
        DocumentoDTO documento,
        DadosPessoaisDTO dadosPessoais) {

    
}
