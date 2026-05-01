CREATE TABLE categoria (
    Id INT NOT NULL AUTO_INCREMENT,
    Nome VARCHAR(100) NOT NULL,
    PRIMARY KEY (Id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE lutador (
    Id INT NOT NULL AUTO_INCREMENT,
    Nome VARCHAR(100) NOT NULL,
    Peso DOUBLE NOT NULL,
    Vitorias INT DEFAULT 0,
    Derrotas INT DEFAULT 0,
    Empates INT DEFAULT 0,
    CategoriaId INT NOT NULL,
    PRIMARY KEY (Id),
    CONSTRAINT lutador_ibfk_1 FOREIGN KEY (CategoriaId) REFERENCES categoria (Id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO categoria (Nome) VALUES ('Peso Pena'), ('Peso Médio'), ('Peso Pesado');

INSERT INTO lutador (Nome, Peso, Vitorias, Derrotas, Empates, CategoriaId) 
VALUES ('Charles Oliveira', 70.3, 34, 9, 1, 1);