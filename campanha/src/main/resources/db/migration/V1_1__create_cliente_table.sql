CREATE TABLE IF NOT EXISTS `cliente` (
   id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
   nome               VARCHAR(255) NOT NULL,
   cpf                VARCHAR(14) NOT NULL,
   data_nascimento    DATE,
   sexo               VARCHAR(10),
   email              VARCHAR(255),
   celular            VARCHAR(20),
   UNIQUE(cpf)
) ENGINE=InnoDB;