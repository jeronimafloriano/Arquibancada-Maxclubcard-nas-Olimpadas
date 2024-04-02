CREATE TABLE IF NOT EXISTS `transacao` (
   id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
   cpf                VARCHAR(14) NOT NULL,
   numero_cartao      VARCHAR(20) NOT NULL,
   tipo               VARCHAR(20),
   valor              numeric(19, 2) NOT NULL
) ENGINE=InnoDB;