package com.alita.medscript.atendimento.repository;

import com.alita.medscript.atendimento.domain.FilaAtendimento;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FilaAtendimentoRepository extends JpaRepository<FilaAtendimento, Long> {

    @Query(value = """
            SELECT *
            FROM fila_atendimento
            WHERE status = :status
            ORDER BY nivel_prioridade ASC, chegada ASC
            LIMIT 1
            """, nativeQuery = true)
    Optional<FilaAtendimento> buscarProximo(@Param("status") String status);

    boolean existsByPacienteIdAndStatus(Long pacienteId, StatusFila status);

    List<FilaAtendimento> findByStatusOrderByNivelPrioridadeAscChegadaAsc(StatusFila status);
}
