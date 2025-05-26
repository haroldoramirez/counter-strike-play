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

    public CompletionStage<EstatisticaJogadorDTO> gerarEstatisticas(Long jogadorId) {

        return obterEstatisticasDoJogador(jogadorId).thenCompose(dto ->

            obterMapaMaisJogado(jogadorId).thenCombine(obterQuantidadeVitorias(jogadorId), (mapa, vitorias) -> {
                dto.setMapaMaisJogado(mapa);
                dto.setQuantidadeVitorias(vitorias);
                return dto;
            }).thenCombine(obterQuantidadeDerrotas(jogadorId), (dtoFinal, derrotas) -> {
                dtoFinal.setQuantidadeDerrotas(derrotas);
                return dtoFinal;
            }).thenCombine(obterQuantidadeEmpates(jogadorId), (dtoFinal, empates) -> {
                dtoFinal.setQuantidadeEmpates(empates);
                return dtoFinal;
            })

        );

    }

    public CompletionStage<EstatisticaJogadorDTO> obterEstatisticasDoJogador(Long jogadorId) {

        return CompletableFuture.supplyAsync(() -> {

            String sql = "select " +
                    "sum(qtd_eliminacoes) as totalQtdEliminacoes, " +
                    "sum(qtd_baixas) as totalQtdBaixas, " +
                    "sum(qtd_dano) as totalQtdDano, " +
                    "max(porcetagem_hs) as totalMaiorPorcetagemHS, " +
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

    public CompletionStage<String> obterMapaMaisJogado(Long jogadorId) {
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

    public CompletionStage<Integer> obterQuantidadeVitorias(Long jogadorId) {
        return consultaContagem("VITORIA", jogadorId);
    }

    public CompletionStage<Integer> obterQuantidadeDerrotas(Long jogadorId) {
        return consultaContagem("DERROTA", jogadorId);
    }

    public CompletionStage<Integer> obterQuantidadeEmpates(Long jogadorId) {
        return consultaContagem("EMPATE", jogadorId);
    }

    private CompletionStage<Integer> consultaContagem(String status, Long jogadorId) {

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