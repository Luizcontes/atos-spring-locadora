drop table if exists `atos_locadora`.`users`;
drop table if exists `atos_locadora`.`authorities`;

CREATE TABLE IF NOT EXISTS `atos_locadora`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `enabled` INT NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE IF NOT EXISTS `atos_locadora`.`authorities` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NULL,
  `authority` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));
