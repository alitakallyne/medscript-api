package com.alita.medscript.patient.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.alita.medscript.patient.domain.Paciente;
import com.alita.medscript.patient.domain.enums.StatusPaciente;
import com.alita.medscript.patient.dto.PacienteFiltroDTO;

public class PacienteSpecification {

    public static Specification<Paciente> comFiltro(PacienteFiltroDTO   filtro) {
        return Specification.where(nomeContem(filtro.nome()))
                .and(cpfContem(filtro.cpf()));
    }


    private static Specification<Paciente> cpfIgual(String cpf) {
        return (root, query, cb) -> {
            if (cpf == null || cpf.isBlank()) {
                return cb.conjunction();
            }
            return cb.equal(root.get("cpf"), cpf);
        };
    }

    private static Specification<Paciente> nomeComecaCom(String nome) {
    return (root, query, cb) -> {
        if (nome == null || nome.isBlank()) {
            return cb.conjunction();
        }
        return cb.like(cb.lower(root.get("nome")), nome.trim().toLowerCase() + "%");
    };
}
    private static Specification<Paciente> nomeContem(String nome) {
        return (root, query, cb) -> {
            if (nome == null || nome.isBlank()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("nome")), "%" + nome.trim().toLowerCase() + "%");
        };
    }

    private static Specification<Paciente> cpfContem(String cpf) {
        return (root, query, cb) -> {
            if (cpf == null || cpf.isBlank()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("cpf")), "%" + cpf.trim().toLowerCase() + "%");
        };
    }
}
