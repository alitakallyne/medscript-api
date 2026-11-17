package com.alita.medscript.clinical.domain.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clinicas")
@Getter
@Setter
public class Clinica {

    @Id 
    @GeneratedValue (strategy  = GenerationType.IDENTITY)
    private Long id;

    private String nomeFantasia;

    private String razaoSocial;

    private String cnpj;

    private String email;

    private String telefone;

    private Boolean ativa;

    private LocalDateTime criadoEm;

    @PrePersist
    public void prePersist() {
        criadoEm = LocalDateTime.now();
        if (ativa == null) {
            ativa = true;
        }
    }
}
