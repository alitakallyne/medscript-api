CREATE TABLE pacientes (
    id BIGSERIAL PRIMARY KEY,

    nome VARCHAR(200) NOT NULL,
    nome_social VARCHAR(200),

    data_nascimento DATE NOT NULL,

    sexo VARCHAR(20),

    cpf VARCHAR(11) NOT NULL UNIQUE,

    falecido BOOLEAN NOT NULL DEFAULT FALSE,

    data_obito DATE,

    email VARCHAR(150) UNIQUE,

    status VARCHAR(20) NOT NULL,

    -- CONTATO

    ddd VARCHAR(3),
    telefone VARCHAR(20),
    telefone2 VARCHAR(20),
    telefone3 VARCHAR(20),

    -- ENDERECO

    logradouro VARCHAR(255),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    estado VARCHAR(2),
    cep VARCHAR(20),
    complemento VARCHAR(255),

    -- DOCUMENTO

    rg VARCHAR(30),
    orgao_emissor VARCHAR(50),
    uf_emissor VARCHAR(2),
    data_expedicao DATE,
    tipo_documento VARCHAR(30),
    numero_documento VARCHAR(50),
    cartao_sus VARCHAR(30),

    -- DADOS PESSOAIS

    nome_pai VARCHAR(200),
    nome_mae VARCHAR(200),
    nome_conjuge VARCHAR(200),

    numero_filhos INTEGER,

    religiao VARCHAR(100),
    estado_civil VARCHAR(50),
    escolaridade VARCHAR(100),

    cor VARCHAR(50),
    etnia VARCHAR(100),
    nacionalidade VARCHAR(100)

    
);

CREATE INDEX idx_paciente_nome
ON pacientes(nome);

CREATE INDEX idx_paciente_status
ON pacientes(status);