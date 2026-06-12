package com.alita.medscript.patient.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.alita.medscript.patient.domain.Paciente;
import com.alita.medscript.patient.domain.enums.StatusPaciente;
import com.alita.medscript.patient.dto.PacienteFiltroDTO;

public class PacienteSpecification {

    public static Specification<Paciente> comFiltro(PacienteFiltroDTO filtro) {
        return Specification.where(nomeContem(filtro.nome()))
                .and(cpfContem(filtro.cpf()))
                .and(ativo(filtro.status()));
    }

    private static Specification<Paciente> ativo(StatusPaciente status) {

        return (root, query, cb) -> {

        if (status == null) {
            return null;
        }

        return cb.equal(root.get("status"), status);
    };
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



    private static Specification<Paciente> nomeContem(String nome) {
        return (root, query, criteriaBuilder) -> {
            if (nome == null || nome.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("nome"), "%" + nome + "%");
        };
    }
}
