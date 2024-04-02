CREATE TABLE IF NOT EXISTS `campanha` (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome            VARCHAR(255) NOT NULL,
    valor_minimo    numeric(19, 2) NOT NULL
) ENGINE=InnoDB;