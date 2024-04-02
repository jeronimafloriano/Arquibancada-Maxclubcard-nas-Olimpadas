CREATE TABLE IF NOT EXISTS `sorteio_participante` (
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_da_sorte   BIGINT,
    campanha_id       BIGINT,
    cliente_id        BIGINT,
    FOREIGN KEY (campanha_id) REFERENCES campanha(id),
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
) ENGINE=InnoDB;