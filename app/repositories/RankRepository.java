package repositories;

import dtos.RankJogadorDTO;
import io.ebean.DB;
import io.ebean.SqlRow;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class RankRepository {

    private final DatabaseExecutionContext executionContext;

    @Inject
    public RankRepository(DatabaseExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    public CompletionStage<List<RankJogadorDTO>> gerarRankJogadores() {

        return CompletableFuture.supplyAsync(() -> {

            String sql = "SELECT " +
                    "j.id AS jogadorId, " +
                    "j.nome AS nome, " +
                    "SUM(r.qtd_eliminacoes) AS totalQtdEliminacoes, " +
                    "SUM(r.qtd_baixas) AS totalQtdBaixas, " +
                    "SUM(r.qtd_dano) AS totalQtdDano, " +
                    "AVG(r.porcetagem_hs) AS totalMaiorPorcetagemHS, " +
                    "SUM(r.qtd_dano_utilitario) AS totalQtdDanoUtilitario, " +
                    "SUM(r.qtd_inimigos_cegos) AS totalQtdInimigosCegos " +
                    "FROM jogador j " +
                    "JOIN registro_partida_jogador r ON r.jogador_id = j.id " +
                    "GROUP BY j.id, j.nome " +
                    "ORDER BY j.nome";

            List<SqlRow> rows = DB.sqlQuery(sql).findList();

            List<RankJogadorDTO> resultados = new ArrayList<>();

            for (SqlRow row : rows) {

                RankJogadorDTO dto = new RankJogadorDTO();

                dto.setNome(row.getString("nome"));
                dto.setTotalQtdEliminacoes(row.getInteger("totalQtdEliminacoes"));
                dto.setTotalQtdBaixas(row.getInteger("totalQtdBaixas"));
                dto.setTotalQtdDano(row.getInteger("totalQtdDano"));
                dto.setTotalMaiorPorcetagemHS(row.getInteger("totalMaiorPorcetagemHS"));
                dto.setTotalQtdDanoUtilitario(row.getInteger("totalQtdDanoUtilitario"));
                dto.setTotalQtdInimigosCegos(row.getInteger("totalQtdInimigosCegos"));

                resultados.add(dto);

            }

            return resultados;

        }, executionContext);

    }

}