package com.alita.medscript.atendimento.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FilaAtendimentoService {

     private final FilaAtendimentoRepository filaRepository;
    private final PacienteRepository pacienteRepository;

    @Transactional
    public FilaAtendimentoResponseDTO entrarNaFila(EntrarNaFilaRequestDTO dto) {
        Paciente paciente = pacienteRepository.findById(dto.pacienteId())
                .orElseThrow(() -> new RegraNegocioException("Paciente nao encontrado"));

        if (filaRepository.existsByPacienteIdAndStatus(paciente.getId(), StatusFila.AGUARDANDO)) {
            throw new RegraNegocioException("Paciente ja esta aguardando na fila");
        }

        FilaAtendimento item = filaRepository.save(new FilaAtendimento(paciente, dto.prioridade()));
        return FilaAtendimentoResponseDTO.de(item);
    }

    @Transactional(readOnly = true)
    public List<FilaAtendimentoResponseDTO> listarAguardando() {
        return filaRepository.findByStatusOrderByNivelPrioridadeAscChegadaAsc(StatusFila.AGUARDANDO)
                .stream()
                .map(FilaAtendimentoResponseDTO::de)
                .toList();
    }

    @Transactional
    public FilaAtendimentoResponseDTO chamarProximo() {
        FilaAtendimento proximo = filaRepository.buscarProximo(StatusFila.AGUARDANDO.name())
                .orElseThrow(() -> new RegraNegocioException("Nao ha pacientes aguardando"));

        proximo.chamar(); // sem save: a JPA grava a mudanca no fim da transacao (dirty checking)
        return FilaAtendimentoResponseDTO.de(proximo);
    }
}
