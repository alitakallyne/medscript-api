-- Experimento de desempenho de índice no PostgreSQL (LIKE / varchar_pattern_ops)
-- Ambiente: container Docker descartável, SEPARADO da aplicação.
--
-- Subir o container:
--   docker run --name pg-teste -e POSTGRES_PASSWORD=teste -p 5433:5432 -d postgres:16
--
-- Conectar:
--   docker exec -it pg-teste psql -U postgres
--
-- Apagar o container ao final:
--   docker rm -f pg-teste

-- =========================================================
-- 1. Criação da tabela
-- =========================================================
CREATE TABLE paciente (
    id bigserial PRIMARY KEY,
    nome varchar(150),
    cpf varchar(11),
    data_nascimento date,
    nome_mae varchar(150),
    status varchar(20)
);

-- =========================================================
-- 2. Inserção de 1 milhão de linhas fictícias
-- =========================================================
INSERT INTO paciente (nome, cpf, data_nascimento, nome_mae, status)
SELECT
    'PACIENTE ' || md5(g::text) AS nome,
    lpad((g % 100000)::text, 11, '0') AS cpf,
    date '1950-01-01' + (g % 25000) * interval '1 day' AS data_nascimento,
    'MAE ' || md5((g + 1)::text) AS nome_mae,
    CASE WHEN g % 2 = 0 THEN 'ATIVO' ELSE 'INATIVO' END AS status
FROM generate_series(1, 1000000) AS g;

ANALYZE paciente;

-- =========================================================
-- 3. Criação do índice (pattern ops, usável por LIKE 'prefixo%')
-- =========================================================
CREATE INDEX idx_paciente_nome ON paciente (nome varchar_pattern_ops);

-- =========================================================
-- 4. EXPLAIN ANALYZE das três consultas
-- =========================================================

-- a) Prefixo conhecido -> pode usar o índice (busca por igualdade/range no início da string)
EXPLAIN ANALYZE
SELECT * FROM paciente WHERE nome LIKE 'PACIENTE a1b2%';

-- b) Padrão com % nas duas pontas -> índice não ajuda (precisa varrer tudo)
EXPLAIN ANALYZE
SELECT * FROM paciente WHERE nome LIKE '%a1b2%';

-- c) Padrão com % nas duas pontas, sem nenhum resultado -> também não usa índice
EXPLAIN ANALYZE
SELECT * FROM paciente WHERE nome LIKE '%XYZQ%';
