INSERT INTO tb_livro (titulo, isbn, editora, ano_publicacao, categoria, quantidade_total, quantidade_disponivel) VALUES ('Clean Code', '978-0132350884', 'Pearson', 2008, 'Programação', 5, 5);
INSERT INTO tb_livro (titulo, isbn, editora, ano_publicacao, categoria, quantidade_total, quantidade_disponivel) VALUES ('Domain-Driven Design', '978-0321125217', 'Addison-Wesley', 2003, 'Arquitetura', 3, 3);

INSERT INTO tb_autor (nome, nacionalidade) VALUES ('Robert C. Martin', 'Americano');
INSERT INTO tb_autor (nome, nacionalidade) VALUES ('Eric Evans', 'Americano');

INSERT INTO tb_livro_autor (livro_id, autor_id) VALUES (1, 1);
INSERT INTO tb_livro_autor (livro_id, autor_id) VALUES (2, 2);

