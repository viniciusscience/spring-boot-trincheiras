CREATE TABLE IF NOT EXISTS persistent_logins
(
    username  VARCHAR(64) NOT NULL,    -- Nome de usuário
    series    VARCHAR(64) PRIMARY KEY, -- Identificador único do token
    token     VARCHAR(64) NOT NULL,    -- Valor do token
    last_used TIMESTAMP   NOT NULL     -- Última vez que o token foi usado
);