CREATE TABLE pessoa (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	naturalidade VARCHAR(50) NOT NULL,
	ativo boolean NOT NULL default true,
	logradouro VARCHAR(255),
	numero VARCHAR(255),
	complemento VARCHAR(255),
	bairro VARCHAR(255),
	cep VARCHAR(255),
	cidade VARCHAR(255),
	estado VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into pessoa (nome, naturalidade, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Fulano 01', 'Cidade 01', true, 'Rua Um', '01', 'Entre Rua Dois e Rua Três', 'Centro', '68900-001', 'Springfield', 'MA');
insert into pessoa (nome, naturalidade, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Beltrano 01', 'Cidade 02', true, 'Rua Dois', '02', 'Entre Rua XX e Rua YY', 'Centro', '68901-001', 'Cidade Dois', 'PA');
insert into pessoa (nome, naturalidade, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Cicrano 01', 'Cidade 03', true, 'Rua Três', '03', 'Entre Rua WW e Rua QQ', 'Centro', '68902-001', 'Cidade Três', 'CA');
insert into pessoa (nome, naturalidade, ativo) values ('João da Silva', 'São Paulo', true);