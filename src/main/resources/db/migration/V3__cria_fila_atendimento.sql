CREATE TABLE fila_atendimento (
    id               BIGSERIAL PRIMARY KEY,
    paciente_id      BIGINT      NOT NULL REFERENCES pacientes(id),
    prioridade       VARCHAR(20) NOT NULL,
    nivel_prioridade INTEGER     NOT NULL,
    chegada          TIMESTAMP   NOT NULL,
    status           VARCHAR(20) NOT NULL
);

CREATE INDEX idx_fila_proximo ON fila_atendimento (status, nivel_prioridade, chegada);