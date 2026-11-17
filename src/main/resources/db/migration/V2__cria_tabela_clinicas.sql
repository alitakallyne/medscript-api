CREATE TABLE clinicas (
    id            BIGSERIAL PRIMARY KEY,
    nome_fantasia VARCHAR(150) NOT NULL,
    razao_social  VARCHAR(200) NOT NULL,
    cnpj          VARCHAR(18)  NOT NULL UNIQUE,
    email         VARCHAR(150),
    telefone      VARCHAR(20),
    ativa         BOOLEAN      NOT NULL DEFAULT TRUE,
    criado_em     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);