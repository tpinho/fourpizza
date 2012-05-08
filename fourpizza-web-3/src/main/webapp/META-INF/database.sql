/* Apaga tabelas já criadas. */
DROP TABLE IF EXISTS recheios_ingredientes CASCADE;
DROP TABLE IF EXISTS recheios CASCADE;
DROP TABLE IF EXISTS ingredientes CASCADE;

/* 
 * Cria tabela de ingredientes com campos, id, nome e valor. 
 * O campo ID é gerado automaticamente pelo banco e 
 * é chave primária 
 * */
CREATE TABLE ingredientes (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, 
	nome VARCHAR(100) NOT NULL UNIQUE, 
	valor_porcao FLOAT
);

CREATE TABLE recheios (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	nome VARCHAR(100) NOT NULL UNIQUE,
	descricao VARCHAR(250),
	valor FLOAT
);

/*
 * Tabela de relacionamento muitos para muitos 
 * entre recheio e ingredientes. 
 */
CREATE TABLE recheios_ingredientes (
	recheio_id INTEGER,
	ingrediente_id INTEGER,
	FOREIGN KEY(recheio_id) REFERENCES recheios(id) ON DELETE CASCADE,
	FOREIGN KEY(ingrediente_id) REFERENCES ingredientes(id),
	PRIMARY KEY (recheio_id, ingrediente_id)
);
