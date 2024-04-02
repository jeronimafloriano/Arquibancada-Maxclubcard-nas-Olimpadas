CREATE TABLE IF NOT EXISTS `cliente_campanha` (
    cliente_id        BIGINT,
    campanha_id       BIGINT,
    numero_da_sorte   BIGINT,
    PRIMARY KEY (cliente_id, campanha_id),
    FOREIGN KEY (cliente_id) REFERENCES cliente(id),
    FOREIGN KEY (campanha_id) REFERENCES campanha(id)
) ENGINE=InnoDB;