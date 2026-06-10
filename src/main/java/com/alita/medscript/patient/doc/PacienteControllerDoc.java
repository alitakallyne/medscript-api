package com.alita.medscript.patient.doc;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.alita.medscript.patient.dto.PacienteFiltroDTO;
import com.alita.medscript.patient.dto.PacienteRequestDTO;
import com.alita.medscript.patient.dto.PacienteResponseDTO;
import com.alita.medscript.patient.dto.PacienteResumoDTO;
import com.alita.medscript.shared.exception.ApiErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@Tag(
name = "Pacientes",
description = "Operações relacionadas ao cadastro e gerenciamento de pacientes"
)
public interface PacienteControllerDoc {

@Operation(
    summary = "Cadastrar paciente",
    description = "Realiza o cadastro de um novo paciente no sistema",
    responses = {
        @ApiResponse(
            responseCode = "201",
            description = "Paciente cadastrado com sucesso"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Erro de validação ou regra de negócio",
            content = @Content(
                schema = @Schema(implementation = ApiErrorResponse.class)
            )
        )
    }
)
ResponseEntity<PacienteResumoDTO> criar(

    @Valid
    @RequestBody(
        description = "Dados do paciente",
        required = true,
        content = @Content(
            schema = @Schema(implementation = PacienteRequestDTO.class),
            examples = @ExampleObject(
                name = "Cadastro de paciente",
                value = """
                {
                  "nome": "João Silva",
                  "nomeSocial": "Joãozinho",
                  "dataNascimento": "1990-05-20",
                  "sexo": "MASCULINO",
                  "cpf": "12345678909",
                  "email": "joao@email.com",
                  "falecido": false
                }
                """
            )
        )
    )
    PacienteRequestDTO dto
);

@Operation(
    summary = "Buscar pacientes",
    description = "Consulta pacientes utilizando filtros de nome, CPF e status",
    responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Pacientes encontrados com sucesso"
        )
    }
)
ResponseEntity<Page<PacienteResumoDTO>> buscar(

    @Parameter(
        description = "Filtro de pesquisa"
    )
    PacienteFiltroDTO filtro,

    Pageable pageable
);

@Operation(
    summary = "Buscar paciente por ID",
    description = "Retorna os dados completos de um paciente"
)
ResponseEntity<PacienteResponseDTO> buscarPorId(

    @Parameter(
        description = "ID do paciente",
        required = true,
        example = "1"
    )
    Long id
);

@Operation(
    summary = "Atualizar paciente",
    description = "Atualiza os dados cadastrais de um paciente",
    responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Paciente atualizado com sucesso"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Paciente não encontrado"
        )
    }
)
ResponseEntity<PacienteResponseDTO> atualizar(

    @Parameter(
        description = "ID do paciente",
        required = true,
        example = "1"
    )
    Long id,

    @Valid
    PacienteRequestDTO dto
);

@Operation(
    summary = "Inativar paciente",
    description = "Realiza a inativação lógica de um paciente",
    responses = {
        @ApiResponse(
            responseCode = "204",
            description = "Paciente inativado com sucesso"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Paciente não encontrado"
        )
    }
)
ResponseEntity<Void> deletar(

    @Parameter(
        description = "ID do paciente",
        required = true,
        example = "1"
    )
    Long id
);

}
