-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema rasbet
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema rasbet
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `rasbet` DEFAULT CHARACTER SET utf8 ;
USE `rasbet` ;

-- -----------------------------------------------------
-- Table `rasbet`.`Aposta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasbet`.`Aposta` (
  `idAposta` VARCHAR(45) NOT NULL,
  `quantia` FLOAT NOT NULL,
  `odd` FLOAT NOT NULL,
  `idResultado` VARCHAR(45) NOT NULL,
  `idApostador` VARCHAR(45) NOT NULL,
  `moeda` VARCHAR(45) NOT NULL,

  PRIMARY KEY (`idAposta`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rasbet`.`Apostador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasbet`.`Apostador` (
  `idApostador` VARCHAR(64) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `dataNascimento` date NOT NULL,
  PRIMARY KEY (`idApostador`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rasbet`.`Participante`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasbet`.`Participante` (
  `idParticipante` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idParticipante`))
ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table `rasbet`.`ResultadoPossivel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasbet`.`ResultadoPossivel` (
  `idResultadoPossivel` VARCHAR(45) NOT NULL,
  `odd` FLOAT NOT NULL,
  `tipoAposta` VARCHAR(45) NOT NULL,
  `descricao` VARCHAR(45) NOT NULL,
  `ganhou` VARCHAR(45) NOT NULL,
  `estado` VARCHAR(45) NOT NULL,
  `participante` VARCHAR(45) NULL,
  `idEvento` VARCHAR(45) NULL,
  PRIMARY KEY (`idResultadoPossivel`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rasbet`.`Moeda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasbet`.`Moeda` (
  `idMoeda` VARCHAR(45) NOT NULL,
  `token` VARCHAR(45) NOT NULL,
  `ratio` FLOAT NOT NULL,
  `imposto` FLOAT NOT NULL,
  PRIMARY KEY (`idMoeda`))
ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table `rasbet`.`Evento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasbet`.`Evento` (
  `idEvento` VARCHAR(45) NOT NULL,
  `desporto` VARCHAR(45) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `estado` VARCHAR(45) NOT NULL,
  `data` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idEvento`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rasbet`.`apostaApostadores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasbet`.`apostaApostadores` (
  `apostador` VARCHAR(20) NULL,
  `aposta` VARCHAR(20) NULL)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `rasbet`.`Carteira`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasbet`.`Carteira` (
  `id` VARCHAR(128) NOT NULL PRIMARY KEY,
  `apostador` VARCHAR(64) NULL,
  `moeda` VARCHAR(64) NULL,
  `valor` FLOAT NULL)
ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table `rasbet`.`eventoResultados`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasbet`.`eventoResultados` (
  `evento` VARCHAR(20) NULL,
  `resultadoPossivel` VARCHAR(20) NULL)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rasbet`.`eventoParticipantes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasbet`.`eventoParticipantes` (
  `evento` VARCHAR(20) NULL,
  `participante` VARCHAR(20) NULL,
  `pontuacao` INT NULL)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
