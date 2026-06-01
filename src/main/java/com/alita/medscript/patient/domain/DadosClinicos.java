package com.alita.medscript.patient.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
  @Embeddable
@Getter @Setter
public class DadosClinicos {

  
    private Double altura;
    private Double peso;
    private Boolean oncologico;
}
