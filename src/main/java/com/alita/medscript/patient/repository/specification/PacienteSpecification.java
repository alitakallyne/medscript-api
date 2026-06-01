package com.alita.medscript.patient.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.alita.medscript.patient.domain.Paciente;
import com.alita.medscript.patient.dto.PacienteFiltroDTO;

public class PacienteSpecification {

    public static Specification<Paciente> comFiltro(PacienteFiltroDTO filtro) {
        return Specification.where(nomeContem(filtro.nome()))
                .and(cpfContem(filtro.cpf()))
                .and(ativoIgual(filtro.ativo()));
    }

    private static Specification<Paciente> cpfContem(String cpf) {
       return 
                (root, query, criteriaBuilder) -> {
                     if (cpf == null || cpf.isEmpty()) {
                          return criteriaBuilder.conjunction();
                     }
                     return criteriaBuilder.like(root.get("cpf"), "%" + cpf + "%");
                };
    }

    private static Specification<Paciente> ativoIgual(Boolean ativo) {
       return (root, query, criteriaBuilder) -> {
            if (ativo == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("ativo"), ativo);
        };
    }

    private static Specification<Paciente> nomeContem(String nome) {
        return (root, query, criteriaBuilder) -> {
            if (nome == null || nome.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("nome"), "%" + nome + "%");
        };
    }
}
