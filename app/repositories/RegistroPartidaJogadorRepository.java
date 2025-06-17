package repositories;

import io.ebean.DB;
import io.ebean.PagedList;
import models.RegistroPartidaJogador;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class RegistroPartidaJogadorRepository {

    private final DatabaseExecutionContext executionContext;

    @Inject
    public RegistroPartidaJogadorRepository(DatabaseExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    /**
     * Retorna uma lista paginada de objetos
     *
     * @param page     Page to display
     * @param pageSize Number of computers per page
     * @param sortBy   Computer property used for sorting
     * @param order    Sort order (either or asc or desc)
     * @param filter   Filter applied on the name column
     */
    public CompletionStage<PagedList<RegistroPartidaJogador>> page(int page, int pageSize, String sortBy, String order, String filter) {

        if (filter != null && !filter.isEmpty() && filter.matches("\\d+")) {
            return supplyAsync(() ->
                DB.find(RegistroPartidaJogador.class)
                    .where()
                    .eq("jogador.id", Long.parseLong(filter))
                    .orderBy(sortBy + " " + order)
                    .setFirstRow(page * pageSize)
                    .setMaxRows(pageSize)
                    .findPagedList(), executionContext);
        } else {
            return supplyAsync(() ->
                DB.find(RegistroPartidaJogador.class)
                    .where()
                    .orderBy(sortBy + " " + order)
                    .setFirstRow(page * pageSize)
                    .setMaxRows(pageSize)
                    .findPagedList(), executionContext);
        }

    }

    /**
     * Salva na base de dados um novo Objeto
     *
     */
    public CompletionStage<Long> insert(RegistroPartidaJogador registroPartidaJogador) {
        return supplyAsync(() -> {
            DB.insert(registroPartidaJogador);
            return registroPartidaJogador.getId();
        }, executionContext);
    }

    /**
     * Return a list of last objects created
     *
     */
    public CompletionStage<PagedList<RegistroPartidaJogador>> last(int page, int pageSize) {

        return supplyAsync(() ->
            DB.find(RegistroPartidaJogador.class)
                .where()
                .orderBy("dataCadastro desc")
                .setFirstRow(page * pageSize)
                .setMaxRows(pageSize)
                .findPagedList(), executionContext);

    }

    /**
     * Salva na base de dados uma lista em lote
     *
     */
    public CompletionStage<Void> insertAll(List<RegistroPartidaJogador> registros) {
        return CompletableFuture.runAsync(() -> {
            DB.saveAll(registros); // inserção em lote
        }, executionContext);
    }

    /**
     * Salva na base de dados uma lista em lote
     *
     */
    public void insertAllSync(List<RegistroPartidaJogador> registros) {
        DB.saveAll(registros); // inserção em lote
    }

    /**
     * Metodo responsavel por obter todos os registros de partida de jogadores
     *
     */
    public List<RegistroPartidaJogador> all() {
        return DB.find(RegistroPartidaJogador.class).findList();
    }

}