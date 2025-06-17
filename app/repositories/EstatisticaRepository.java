package repositories;

import dtos.EstatisticaJogadorDTO;
import io.ebean.DB;
import io.ebean.SqlRow;
import models.Mapa;

import javax.inject.Inject;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class EstatisticaRepository {

    private final DatabaseExecutionContext executionContext;

    @Inject
    public EstatisticaRepository(DatabaseExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    //Logica Estatisticas by Nome do Jogador
    public CompletionStage<EstatisticaJogadorDTO> gerarEstatisticas(String nome) {

        return obterEstatisticasDoJogadorByNomeJogador(nome).thenCompose(dto ->

            obterMapaMaisJogadoByNomeJogador(nome).thenCombine(obterQuantidadeVitoriasByNomeJogador(nome), (mapa, vitorias) -> {

                dto.setMapaMaisJogado(mapa);
                dto.setQuantidadeVitorias(vitorias);

                return dto;

            }).thenCombine(obterQuantidadeDerrotasByNomeJogador(nome), (dtoFinal, derrotas) -> {
                dtoFinal.setQuantidadeDerrotas(derrotas);
                return dtoFinal;
            }).thenCombine(obterQuantidadeEmpatesByNomeJogador(nome), (dtoFinal, empates) -> {
                dtoFinal.setQuantidadeEmpates(empates);
                return dtoFinal;
            })

        );

    }

    public CompletionStage<EstatisticaJogadorDTO> obterEstatisticasDoJogadorByNomeJogador(String nome) {

        return CompletableFuture.supplyAsync(() -> {

            String sql = "SELECT " +
                "UPPER(j.nome) AS nomeJogador, " +
                "SUM(r.qtd_eliminacoes) AS totalQtdEliminacoes, " +
                "SUM(r.qtd_baixas) AS totalQtdBaixas, " +
                "SUM(r.qtd_dano) AS totalQtdDano, " +
                "AVG(r.porcetagem_hs) AS totalMaiorPorcetagemHS, " +
                "SUM(r.qtd_dano_utilitario) AS totalQtdDanoUtilitario, " +
                "SUM(r.qtd_inimigos_cegos) AS totalQtdInimigosCegos " +
                "FROM registro_partida_jogador r " +
                "INNER JOIN jogador j ON r.jogador_id = j.id " +
                "WHERE UPPER(j.nome) = :nome " +
                "GROUP BY UPPER(j.nome)";

            SqlRow row = DB.sqlQuery(sql)
                    .setParameter("nome", nome)
                    .findOne();

            EstatisticaJogadorDTO dto = new EstatisticaJogadorDTO();

            if (row != null) {
                dto.setNomeJogador(Objects.requireNonNullElse(row.getString("nomeJogador"), null));
                dto.setTotalQtdEliminacoes(Objects.requireNonNullElse(row.getInteger("totalQtdEliminacoes"), 0));
                dto.setTotalQtdBaixas(Objects.requireNonNullElse(row.getInteger("totalQtdBaixas"), 0));
                dto.setTotalQtdDano(Objects.requireNonNullElse(row.getInteger("totalQtdDano"), 0));
                dto.setTotalMaiorPorcetagemHS(Objects.requireNonNullElse(row.getInteger("totalMaiorPorcetagemHS"), 0));
                dto.setTotalQtdDanoUtilitario(Objects.requireNonNullElse(row.getInteger("totalQtdDanoUtilitario"), 0));
                dto.setTotalQtdInimigosCegos(Objects.requireNonNullElse(row.getInteger("totalQtdInimigosCegos"), 0));
            }

            return dto;

        }, executionContext);

    }

    public CompletionStage<String> obterMapaMaisJogadoByNomeJogador(String nome) {

        return CompletableFuture.supplyAsync(() -> {

            String sql = "SELECT r.mapa_id, count(*) as total " +
                    "FROM registro_partida_jogador r " +
                    "INNER JOIN jogador j ON r.jogador_id = j.id " +
                    "WHERE UPPER(j.nome) = :nome " +
                    "group by r.mapa_id " +
                    "order by total desc limit 1";

            SqlRow row = DB.sqlQuery(sql)
                    .setParameter("nome", nome)
                    .findOne();

            if (row != null) {

                Long mapaId = row.getLong("mapa_id");

                return DB.find(Mapa.class).setId(mapaId).findOneOrEmpty()
                        .map(Mapa::getNome)
                        .orElse("Desconhecido");

            }

            return "Desconhecido";

        }, executionContext);
    }

    public CompletionStage<Integer> obterQuantidadeVitoriasByNomeJogador(String nome) {
        return consultaContagemByNomeJogador("VITORIA", nome);
    }

    public CompletionStage<Integer> obterQuantidadeDerrotasByNomeJogador(String nome) {
        return consultaContagemByNomeJogador("DERROTA", nome);
    }

    public CompletionStage<Integer> obterQuantidadeEmpatesByNomeJogador(String nome) {
        return consultaContagemByNomeJogador("EMPATE", nome);
    }

    private CompletionStage<Integer> consultaContagemByNomeJogador(String status, String nome) {

        return CompletableFuture.supplyAsync(() -> {

            String sql = "SELECT count(*) as total " +
                "FROM registro_partida_jogador r " +
                "INNER JOIN jogador j ON r.jogador_id = j.id " +
                "WHERE UPPER(j.nome) = :nome AND r.status_partida = :status";

            SqlRow row = DB.sqlQuery(sql)
                    .setParameter("nome", nome)
                    .setParameter("status", status)
                    .findOne();

            return row != null ? row.getInteger("total") : 0;
        }, executionContext);

    }

    //Logica Estatisticas by ID Jogador
    public CompletionStage<EstatisticaJogadorDTO> gerarEstatisticas(Long jogadorId) {

        return obterEstatisticasDoJogadorByIdJogador(jogadorId).thenCompose(dto ->

            obterMapaMaisJogadoByIdJogador(jogadorId).thenCombine(obterQuantidadeVitoriasByIdJogador(jogadorId), (mapa, vitorias) -> {
                dto.setMapaMaisJogado(mapa);
                dto.setQuantidadeVitorias(vitorias);
                return dto;
            }).thenCombine(obterQuantidadeDerrotasByIdJogador(jogadorId), (dtoFinal, derrotas) -> {
                dtoFinal.setQuantidadeDerrotas(derrotas);
                return dtoFinal;
            }).thenCombine(obterQuantidadeEmpatesByIdJogador(jogadorId), (dtoFinal, empates) -> {
                dtoFinal.setQuantidadeEmpates(empates);
                return dtoFinal;
            })

        );

    }

    public CompletionStage<EstatisticaJogadorDTO> obterEstatisticasDoJogadorByIdJogador(Long jogadorId) {

        return CompletableFuture.supplyAsync(() -> {

            String sql = "select " +
                    "sum(qtd_eliminacoes) as totalQtdEliminacoes, " +
                    "sum(qtd_baixas) as totalQtdBaixas, " +
                    "sum(qtd_dano) as totalQtdDano, " +
                    "avg(porcetagem_hs) as totalMaiorPorcetagemHS, " +
                    "sum(qtd_dano_utilitario) as totalQtdDanoUtilitario, " +
                    "sum(qtd_inimigos_cegos) as totalQtdInimigosCegos " +
                    "from registro_partida_jogador " +
                    "where jogador_id = :jogadorId";

            SqlRow row = DB.sqlQuery(sql)
                    .setParameter("jogadorId", jogadorId)
                    .findOne();

            EstatisticaJogadorDTO dto = new EstatisticaJogadorDTO();

            if (row != null) {
                dto.setTotalQtdEliminacoes(Objects.requireNonNullElse(row.getInteger("totalQtdEliminacoes"), 0));
                dto.setTotalQtdBaixas(Objects.requireNonNullElse(row.getInteger("totalQtdBaixas"), 0));
                dto.setTotalQtdDano(Objects.requireNonNullElse(row.getInteger("totalQtdDano"), 0));
                dto.setTotalMaiorPorcetagemHS(Objects.requireNonNullElse(row.getInteger("totalMaiorPorcetagemHS"), 0));
                dto.setTotalQtdDanoUtilitario(Objects.requireNonNullElse(row.getInteger("totalQtdDanoUtilitario"), 0));
                dto.setTotalQtdInimigosCegos(Objects.requireNonNullElse(row.getInteger("totalQtdInimigosCegos"), 0));
            }

            return dto;
        }, executionContext);

    }

    public CompletionStage<String> obterMapaMaisJogadoByIdJogador(Long jogadorId) {
        return CompletableFuture.supplyAsync(() -> {
            String sql = "select mapa_id, count(*) as total " +
                    "from registro_partida_jogador " +
                    "where jogador_id = :jogadorId " +
                    "group by mapa_id " +
                    "order by total desc limit 1";

            SqlRow row = DB.sqlQuery(sql)
                    .setParameter("jogadorId", jogadorId)
                    .findOne();

            if (row != null) {

                Long mapaId = row.getLong("mapa_id");
                return DB.find(Mapa.class).setId(mapaId).findOneOrEmpty()
                        .map(Mapa::getNome)
                        .orElse("Desconhecido");

            }

            return "Desconhecido";
        }, executionContext);
    }

    public CompletionStage<Integer> obterQuantidadeVitoriasByIdJogador(Long jogadorId) {
        return consultaContagemByIdJogador("VITORIA", jogadorId);
    }

    public CompletionStage<Integer> obterQuantidadeDerrotasByIdJogador(Long jogadorId) {
        return consultaContagemByIdJogador("DERROTA", jogadorId);
    }

    public CompletionStage<Integer> obterQuantidadeEmpatesByIdJogador(Long jogadorId) {
        return consultaContagemByIdJogador("EMPATE", jogadorId);
    }

    private CompletionStage<Integer> consultaContagemByIdJogador(String status, Long jogadorId) {

        return CompletableFuture.supplyAsync(() -> {

            String sql = "select count(*) as total " +
                    "from registro_partida_jogador " +
                    "where jogador_id = :jogadorId and status_partida = :status";

            SqlRow row = DB.sqlQuery(sql)
                    .setParameter("jogadorId", jogadorId)
                    .setParameter("status", status)
                    .findOne();

            return row != null ? row.getInteger("total") : 0;
        }, executionContext);

    }

}