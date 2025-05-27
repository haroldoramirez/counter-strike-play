# --- Sample dataset

# --- !Ups

INSERT INTO jogador (nome, data_cadastro, data_alteracao) VALUES ('Pumba', '2025-05-18 11:40:00', '2025-05-18 11:40:00');
INSERT INTO jogador (nome, data_cadastro, data_alteracao) VALUES ('Kaduzera', '2025-05-18 11:40:00', '2025-05-18 11:40:00');
INSERT INTO jogador (nome, data_cadastro, data_alteracao) VALUES ('MASSAS VENEZA', '2025-05-18 11:40:00', '2025-05-18 11:40:00');
INSERT INTO jogador (nome, data_cadastro, data_alteracao) VALUES ('Zoid', '2025-05-21 12:40:00', '2025-05-21 12:40:00');
INSERT INTO jogador (nome, data_cadastro, data_alteracao) VALUES ('BeBe GoRiLa™', '2025-05-24 16:40:00', '2025-05-24 16:40:00');
INSERT INTO jogador (nome, data_cadastro, data_alteracao) VALUES ('Raul Picadi Mel', '2025-05-24 16:40:00', '2025-05-24 16:40:00');
INSERT INTO jogador (nome, data_cadastro, data_alteracao) VALUES ('Jalin Tupi Dipal', '2025-05-24 16:40:00', '2025-05-24 16:40:00');
INSERT INTO jogador (nome, data_cadastro, data_alteracao) VALUES ('Neto?', '2025-05-24 16:40:00', '2025-05-24 16:40:00');

INSERT INTO mapa (nome, data_cadastro, data_alteracao) VALUES ('Dust 2', '2025-05-24 16:40:00', '2025-05-24 16:40:00');
INSERT INTO mapa (nome, data_cadastro, data_alteracao) VALUES ('Overpass', '2025-05-24 16:40:00', '2025-05-24 16:40:00');
INSERT INTO mapa (nome, data_cadastro, data_alteracao) VALUES ('Inferno', '2025-05-24 16:40:00', '2025-05-24 16:40:00');
INSERT INTO mapa (nome, data_cadastro, data_alteracao) VALUES ('Anubis', '2025-05-24 16:40:00', '2025-05-24 16:40:00');
INSERT INTO mapa (nome, data_cadastro, data_alteracao) VALUES ('Ancient', '2025-05-24 16:40:00', '2025-05-24 16:40:00');
INSERT INTO mapa (nome, data_cadastro, data_alteracao) VALUES ('Office', '2025-05-24 16:40:00', '2025-05-24 16:40:00');
INSERT INTO mapa (nome, data_cadastro, data_alteracao) VALUES ('Cache', '2025-05-24 16:40:00', '2025-05-24 16:40:00');
INSERT INTO mapa (nome, data_cadastro, data_alteracao) VALUES ('Mirage', '2025-05-24 16:40:00', '2025-05-24 16:40:00');

INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (1, 1, 'VITORIA', 20, 19, 1400, 34, 54, 16, '2025-05-24 16:40:00', '2025-05-24 16:40:00');