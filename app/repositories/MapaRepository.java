package repositories;

import io.ebean.DB;
import io.ebean.PagedList;
import io.ebean.Transaction;
import models.Mapa;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class MapaRepository {

    private final DatabaseExecutionContext executionContext;

    @Inject
    public MapaRepository(DatabaseExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    /**
     * Retorna objeto pelo identificador
     *
     * @param id identificador
     */
    public CompletionStage<Optional<Mapa>> obterMapaById(Long id) {
        return supplyAsync(() -> DB.find(Mapa.class).setId(id).findOneOrEmpty(), executionContext);
    }

    /**
     * Retorna objeto pelo nome
     *
     * @param nome nome do objeto
     */
    public CompletionStage<Optional<Mapa>> obterMapaPorNome(String nome) {
        return supplyAsync(() ->
            Optional.ofNullable(
                DB.find(Mapa.class)
                    .where()
                    .eq("nome", nome)
                    .findOne()
            ), executionContext
        );
    }

    /**
     * Retorna uma lista paginada
     *
     * @param page     Page to display
     * @param pageSize Number of computers per page
     * @param sortBy   Computer property used for sorting
     * @param order    Sort order (either or asc or desc)
     * @param filter   Filter applied on the name column
     */
    public CompletionStage<PagedList<Mapa>> page(int page, int pageSize, String sortBy, String order, String filter) {
        return supplyAsync(() ->
            DB.find(Mapa.class)
                .where()
                .ilike("nome", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .setFirstRow(page * pageSize)
                .setMaxRows(pageSize)
                .findPagedList(), executionContext);
    }

    /**
     * Salva na base de dados um novo Objeto
     *
     * @param mapa
     */
    public CompletionStage<Long> insert(Mapa mapa) {
        return supplyAsync(() -> {
            DB.insert(mapa);
            return mapa.getId();
        }, executionContext);
    }

    /**
     * Atualiza na base de dados objeto pelo identificador
     *
     * @param id           Identificador
     * @param novoMapa Objeto já validado
     */
    public CompletionStage<Optional<Long>> update(Long id, Mapa novoMapa) {
        return supplyAsync(() -> {

            Optional<Long> value = Optional.empty();

            try (Transaction txn = DB.beginTransaction()) {

                Mapa MapaSalvo = DB.find(Mapa.class).setId(id).findOne();

                if (MapaSalvo != null) {
                    MapaSalvo.setNome(novoMapa.getNome());
                    MapaSalvo.setDataAlteracao(novoMapa.getDataAlteracao());
                    MapaSalvo.update(txn);
                    txn.commit();
                    value = Optional.of(id);
                }

            }

            return value;
        }, executionContext);
    }

    public CompletionStage<Map<String, String>> options() {
        return supplyAsync(() -> DB.find(Mapa.class).orderBy("nome").findList(), executionContext)
            .thenApply(list -> {
                HashMap<String, String> options = new LinkedHashMap<String, String>();
                for (Mapa m : list) {
                    options.put(m.getId().toString(), m.getNome());
                }
                return options;
            });
    }

    public Optional<Mapa> obterMapaPorNomeSync(String nome) {

        return Optional.ofNullable(
            DB.find(Mapa.class)
                .where()
                .eq("nome", nome)
                .findOne()
        );

    }

}