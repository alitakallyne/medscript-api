CREATE TABLE pacientes (
    id                          BIGSERIAL PRIMARY KEY,
    nome_completo               VARCHAR(200) NOT NULL,
    cpf                         VARCHAR(11) UNIQUE,
    data_nascimento             DATE NOT NULL,
    nome_mae                    VARCHAR(200),
    sexo                        VARCHAR(20),
    telefone_contato            VARCHAR(20),
    contato_emergencia_nome     VARCHAR(150),
    contato_emergencia_telefone VARCHAR(20),
    alergias                    VARCHAR(500),
    status                      VARCHAR(20) NOT NULL
);

CREATE INDEX idx_paciente_nome
    ON pacientes (lower(nome_completo) varchar_pattern_ops);