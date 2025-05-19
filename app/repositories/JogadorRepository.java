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
     * Retorna um jogador pelo id
     *
     * @param id     Page to display
     */
    public CompletionStage<Optional<Jogador>> lookup(Long id) {
        return supplyAsync(() -> DB.find(Jogador.class).setId(id).findOneOrEmpty(), executionContext);
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
     * Salva na base de dados um novo Jogador
     *
     * @param jogador     Objeto Jogado ja validado
     */
    public CompletionStage<Long> insert(Jogador jogador) {
        return supplyAsync(() -> {
            DB.insert(jogador);
            return jogador.getId();
        }, executionContext);
    }

    /**
     * Atualiza na base de dados um Jogador
     *
     * @param id           Identificador
     * @param novoJogador  Objeto Jogador já validado
     */
    public CompletionStage<Optional<Long>> update(Long id, Jogador novoJogador) {
        return supplyAsync(() -> {

            Optional<Long> value = Optional.empty();

            try (Transaction txn = DB.beginTransaction()) {

                Jogador jogadorSalvo = DB.find(Jogador.class).setId(id).findOne();

                if (jogadorSalvo != null) {
                    jogadorSalvo.setNome(novoJogador.getNome());
                    jogadorSalvo.update(txn);
                    txn.commit();
                    value = Optional.of(id);
                }

            }

            return value;
        }, executionContext);
    }

}
