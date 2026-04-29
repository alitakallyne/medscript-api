package com.alita.medscript.paciente.service;

import org.springframework.stereotype.Service;

import com.alita.medscript.paciente.domain.Paciente;
import com.alita.medscript.paciente.dto.PacienteRequestDTO;
import com.alita.medscript.paciente.dto.PacienteResponseDTO;
import com.alita.medscript.paciente.dto.PacienteResumoDTO;
import com.alita.medscript.paciente.mapper.PacienteMapper;
import com.alita.medscript.paciente.repository.PacienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteService {

     private final PacienteRepository repository;

    public PacienteResumoDTO  criar(PacienteRequestDTO dto) {
  validarCpfUnico(dto.cpf());

    Paciente paciente = PacienteMapper.toEntity(dto);

    Paciente salvo = repository.save(paciente);

    return PacienteMapper.toResumoDTO(salvo);
    }

    private void validarCpfUnico(String cpf) {
        repository.findByCpf(cpf)
                .ifPresent(p -> {
                    throw new RuntimeException("CPF já cadastrado");
                });
    }
}
