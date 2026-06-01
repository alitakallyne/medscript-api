package com.alita.medscript.patient.service;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alita.medscript.patient.domain.Paciente;
import com.alita.medscript.patient.domain.enums.StatusPaciente;
import com.alita.medscript.patient.dto.PacienteFiltroDTO;
import com.alita.medscript.patient.dto.PacienteRequestDTO;
import com.alita.medscript.patient.dto.PacienteResponseDTO;
import com.alita.medscript.patient.dto.PacienteResumoDTO;
import com.alita.medscript.patient.mapper.PacienteMapper;
import com.alita.medscript.patient.repository.PacienteRepository;
import com.alita.medscript.patient.repository.specification.PacienteSpecification;
import com.alita.medscript.patient.service.validation.CpfValidator;
import com.alita.medscript.patient.service.validation.EmailValidator;
import com.alita.medscript.patient.service.validation.PacienteBusinessValidator;
import com.alita.medscript.patient.service.validation.PacienteDuplicidadeValidator;
import com.alita.medscript.shared.exception.BusinessException;
import com.alita.medscript.shared.exception.ResourceNotFoundException;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository repository;
    private final CpfValidator cpfValidator;
    private final EmailValidator emailValidator;
    private final PacienteBusinessValidator businessValidator;
    private final PacienteDuplicidadeValidator duplicidadeValidator;

    @Transactional
    public PacienteResumoDTO criar(PacienteRequestDTO dto) {

        Paciente paciente = PacienteMapper.toEntity(dto);

        cpfValidator.validar(dto.cpf());
        emailValidator.validar(dto.email());

        duplicidadeValidator.validarCpfUnico(dto.cpf());

        businessValidator.validar(paciente);

        Paciente salvo = repository.save(paciente);

        return PacienteMapper.toResumoDTO(salvo);
    }

    @Transactional(readOnly = true)
    public Page<PacienteResumoDTO> buscar(
            PacienteFiltroDTO filtro,
            Pageable pageable) {

        return repository
                .findAll(
                        PacienteSpecification.comFiltro(filtro),
                        pageable)
                .map(PacienteMapper::toResumoDTO);
    }

    @Transactional(readOnly = true)
    public PacienteResponseDTO buscarPacientePorId(Long id) {
        Paciente paciente = buscarPorId(id);

        return PacienteMapper.toDetalheDTO(paciente);
    }

    @Transactional
    public PacienteResponseDTO atualizar(
            Long id,
            PacienteRequestDTO dto) {

        Paciente paciente = buscarPorId(id);

        if (!Objects.equals(
                paciente.getCpf(),
                dto.cpf())) {

            cpfValidator.validar(dto.cpf());

            duplicidadeValidator.validarCpfUnico(
                    dto.cpf());
        }

        if (!Objects.equals(
                paciente.getEmail(),
                dto.email())) {

            emailValidator.validar(dto.email());
        }

        PacienteMapper.atualizarEntity(
                paciente,
                dto);

        businessValidator.validar(paciente);

        Paciente atualizado = repository.save(paciente);

        return PacienteMapper.toDetalheDTO(
                atualizado);
    }

    private Paciente buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Paciente não encontrado"));

    }

    @Transactional
    public void inativarPaciente(Long id) {
        Paciente paciente = buscarPorId(id);
        if (paciente.getStatus() == StatusPaciente.INATIVO) {
            throw new BusinessException(
                    "Paciente já está inativo");
        }
        paciente.setStatus(StatusPaciente.INATIVO);
        repository.save(paciente);
    }
}
