# --- Sample dataset

# --- !Ups

INSERT INTO jogador (nome, data_cadastro, data_alteracao) VALUES ('Pumba', '2025-05-18 11:40:00', '2025-05-18 11:40:00');
INSERT INTO jogador (nome, data_cadastro, data_alteracao) VALUES ('Kaduzera', '2025-05-18 11:40:00', '2025-05-18 11:40:00');
INSERT INTO jogador (nome, data_cadastro, data_alteracao) VALUES ('MASSAS VENEZA', '2025-05-18 11:40:00', '2025-05-18 11:40:00');
INSERT INTO jogador (nome, data_cadastro, data_alteracao) VALUES ('Zoid', '2025-05-21 12:40:00', '2025-05-21 12:40:00');
INSERT INTO jogador (nome, data_cadastro, data_alteracao) VALUES ('BeBe GoRiLa™', '2025-05-24 16:40:00', '2025-05-24 16:40:00');
INSERT INTO jogador (nome, data_cadastro, data_alteracao) VALUES ('Raul Picadi Mel', '2025-05-24 16:40:00', '2025-05-24 16:40:00');
INSERT INTO jogador (nome, data_cadastro, data_alteracao) VALUES ('Jalin Tupi Dipal', '2025-05-24 16:40:00', '2025-05-24 16:40:00');
INSERT INTO jogador (nome, data_cadastro, data_alteracao) VALUES ('Fera', '2025-05-24 16:40:00', '2025-05-24 16:40:00');
INSERT INTO jogador (nome, data_cadastro, data_alteracao) VALUES ('neto?', '2025-05-24 16:40:00', '2025-05-24 16:40:00');

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
INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (1, 4, 'EMPATE', 33, 65, 5000, 80, 56, 14, '2025-05-24 16:40:00', '2025-05-24 16:40:00');
INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (1, 2, 'DERROTA', 65, 65, 3000, 65, 21, 41, '2025-05-24 16:40:00', '2025-05-24 16:40:00');
INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (4, 5, 'VITORIA', 55, 76, 5556, 55, 2, 51, '2025-05-26 16:40:00', '2025-05-26 16:40:00');
INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (7, 1, 'VITORIA', 62, 31, 1754, 75, 54, 89, '2025-05-24 16:40:00', '2025-05-24 16:40:00');
INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (3, 2, 'EMPATE', 22, 33, 5340, 43, 56, 32, '2025-05-24 16:40:00', '2025-05-24 16:40:00');
INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (4, 3, 'DERROTA', 23, 25, 7643, 10, 21, 65, '2025-05-24 16:40:00', '2025-05-24 16:40:00');
INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (5, 6, 'VITORIA', 75, 0, 2356, 43, 75, 34, '2025-05-26 16:40:00', '2025-05-26 16:40:00');
INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (9, 7, 'VITORIA', 11, 22, 3333, 44, 55, 66, '2025-05-26 16:40:00', '2025-05-26 16:40:00');
INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (8, 5, 'DERROTA', 99, 88, 7777, 66, 44, 55, '2025-05-28 10:40:00', '2025-05-28 16:40:00');
INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (7, 7, 'EMPATE', 77, 88, 9999, 11, 22, 33, '2025-05-27 16:40:00', '2025-05-27 16:40:00');

INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (1, 1, 'VITORIA', 21, 19, 2000, 34, 54, 16, '2025-05-25 16:40:00', '2025-05-25 16:40:00');
INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (1, 1, 'VITORIA', 22, 18, 6900, 34, 54, 16, '2025-05-26 16:40:00', '2025-05-26 16:40:00');
INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (1, 1, 'VITORIA', 23, 17, 5800, 34, 54, 16, '2025-05-27 16:40:00', '2025-05-27 16:40:00');
INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (1, 1, 'VITORIA', 24, 16, 4700, 34, 54, 16, '2025-05-28 16:40:00', '2025-05-28 16:40:00');
INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (1, 1, 'VITORIA', 25, 15, 3600, 34, 54, 16, '2025-05-29 16:40:00', '2025-05-29 16:40:00');
INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (1, 1, 'VITORIA', 26, 14, 2500, 34, 54, 16, '2025-05-30 16:40:00', '2025-05-30 16:40:00');

INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (1, 1, 'EMPATE', 12, 19, 2000, 34, 54, 16, '2025-05-25 16:40:00', '2025-05-25 16:40:00');
INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (1, 1, 'EMPATE', 21, 18, 6900, 34, 54, 16, '2025-05-26 16:40:00', '2025-05-26 16:40:00');
INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (1, 1, 'EMPATE', 22, 17, 5800, 34, 54, 16, '2025-05-27 16:40:00', '2025-05-27 16:40:00');
INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (1, 1, 'EMPATE', 33, 16, 4700, 34, 54, 16, '2025-05-28 16:40:00', '2025-05-28 16:40:00');
INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (1, 1, 'DERROTA', 44, 15, 3600, 34, 54, 16, '2025-05-29 16:40:00', '2025-05-29 16:40:00');
INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (1, 1, 'DERROTA', 55, 14, 2500, 34, 54, 16, '2025-05-30 16:40:00', '2025-05-30 16:40:00');
INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (2, 3, 'DERROTA', 55, 14, 2500, 34, 54, 16, '2025-05-30 16:40:00', '2025-05-30 16:40:00');
INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (3, 4, 'DERROTA', 55, 14, 2500, 34, 54, 16, '2025-05-30 16:40:00', '2025-05-30 16:40:00');
INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (5, 6, 'DERROTA', 55, 14, 2500, 34, 54, 16, '2025-05-30 16:40:00', '2025-05-30 16:40:00');
INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (7, 7, 'DERROTA', 55, 14, 2500, 34, 54, 16, '2025-05-30 16:40:00', '2025-05-30 16:40:00');
INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (9, 6, 'DERROTA', 55, 14, 2500, 34, 54, 16, '2025-05-30 16:40:00', '2025-05-30 16:40:00');
INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (6, 6, 'VITORIA', 55, 14, 2500, 34, 54, 16, '2025-05-30 16:40:00', '2025-05-30 16:40:00');
INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (6, 3, 'VITORIA', 55, 14, 2500, 34, 54, 16, '2025-05-30 16:40:00', '2025-05-30 16:40:00');
INSERT INTO registro_partida_jogador (jogador_id, mapa_id, status_partida, qtd_eliminacoes, qtd_baixas, qtd_dano, porcetagem_hs, qtd_dano_utilitario, qtd_inimigos_cegos, data_cadastro, data_alteracao)
VALUES (6, 5, 'VITORIA', 55, 14, 2500, 34, 54, 16, '2025-05-30 16:40:00', '2025-05-30 16:40:00');