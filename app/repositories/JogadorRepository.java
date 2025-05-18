package repositories;

import io.ebean.DB;
import io.ebean.PagedList;
import io.ebean.Transaction;
import models.Jogador;

import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class JogadorRepository {

    private final DatabaseExecutionContext executionContext;

    @Inject
    public JogadorRepository(DatabaseExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    /**
     * Retorna uma lista paginada de Jogadores
     *
     * @param page     Page to display
     * @param pageSize Number of computers per page
     * @param sortBy   Computer property used for sorting
     * @param order    Sort order (either or asc or desc)
     * @param filter   Filter applied on the name column
     */
    public CompletionStage<PagedList<Jogador>> page(int page, int pageSize, String sortBy, String order, String filter) {
        return supplyAsync(() ->
            DB.find(Jogador.class)
                .where()
                .ilike("nome", "%" + filter + "%") // Corrigido também o campo "name" para "nome"
                .orderBy(sortBy + " " + order)
                .setFirstRow(page * pageSize)
                .setMaxRows(pageSize)
                .findPagedList(), executionContext);
    }

    /**
     * Salve na base de dados um novo Jogador
     *
     * @param jogador     Objeto Jogado ja validado
     */
    public CompletionStage<Long> insert(Jogador jogador) {
        return supplyAsync(() -> {
            jogador.setId(System.currentTimeMillis()); // not ideal, but it works
            DB.insert(jogador);
            return jogador.getId();
        }, executionContext);
    }

    /**
     * Atualiza na base de dados um Jogador
     *
     * @param id     Identificador
     * @param novoJogador     Objeto Jogado ja validado
     */
    public CompletionStage<Optional<Long>> update(Long id, Jogador novoJogador) {
        return supplyAsync(() -> {
            Transaction txn = DB.beginTransaction();
            Optional<Long> value = Optional.empty();
            try {
                Jogador jogadorSalvo = DB.find(Jogador.class).setId(id).findOne();
                if (jogadorSalvo != null) {
                    //TODO feito cast
                    jogadorSalvo.update((Transaction) novoJogador);
                    txn.commit();
                    value = Optional.of(id);
                }
            } finally {
                txn.end();
            }
            return value;
        }, executionContext);
    }

}
