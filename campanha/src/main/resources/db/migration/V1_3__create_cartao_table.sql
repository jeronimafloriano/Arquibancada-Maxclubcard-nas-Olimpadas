CREATE TABLE IF NOT EXISTS `cartao` (
    id BIGINT         AUTO_INCREMENT PRIMARY KEY,
    cliente_id        BIGINT,
    numero            VARCHAR(20) NOT NULL,
    tipo              VARCHAR(20),
    bandeira          VARCHAR(20),
    data_expiracao    DATE,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
) ENGINE=InnoDB;