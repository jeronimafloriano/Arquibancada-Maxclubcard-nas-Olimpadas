CREATE TABLE IF NOT EXISTS `usuario` (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    username              VARCHAR(20) NOT NULL,
    password              VARCHAR(255) NOT NULL,
    UNIQUE(username)
) ENGINE=InnoDB;