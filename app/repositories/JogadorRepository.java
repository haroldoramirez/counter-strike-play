package repositories;

import io.ebean.DB;
import io.ebean.PagedList;
import io.ebean.Transaction;
import models.Jogador;
import play.db.ebean.Transactional;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.logging.Logger;

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
    public CompletionStage<Optional<Jogador>> obterJogadorById(Long id) {
        return supplyAsync(() -> DB.find(Jogador.class).setId(id).findOneOrEmpty(), executionContext);
    }

    /**
     * Retorna um jogador pelo nome
     *
     * @param nome Nome do jogador a buscar
     */
    public CompletionStage<Optional<Jogador>> obterJogadorPorNome(String nome) {
        return supplyAsync(() ->
            Optional.ofNullable(
                DB.find(Jogador.class)
                    .where()
                    .eq("nome", nome)
                    .findOne()
            ), executionContext
        );
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
                .ilike("nome", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .setFirstRow(page * pageSize)
                .setMaxRows(pageSize)
                .findPagedList(), executionContext);
    }

    /**
     * Salva na base de dados um novo Jogador
     *
     * @param jogador Objeto ja validado retorna apenas o Id
     */
    public CompletionStage<Long> insert(Jogador jogador) {
        return supplyAsync(() -> {
            DB.insert(jogador);
            return jogador.getId();
        }, executionContext);
    }

    /**
     * Salva na base de dados um novo Jogador
     *
     * @param jogador Objeto ja validado retorna o objeto
     */
    public CompletionStage<Jogador> insertSemId(Jogador jogador) {
        return supplyAsync(() -> {
            DB.insert(jogador);
            return jogador; // retorna o objeto completo já com ID preenchido
        }, executionContext);
    }

    public CompletionStage<Jogador> salvarComTransacaoAsync(Jogador jogador) {
        return CompletableFuture.supplyAsync(() -> {
            try (Transaction txn = DB.beginTransaction()) {
                DB.insert(jogador);
                txn.commit();
                return jogador;
            } catch (Exception e) {
                e.printStackTrace(); // ou use log.error(...)
                return null;
            }
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
                    jogadorSalvo.setDataAlteracao(novoJogador.getDataAlteracao());
                    jogadorSalvo.update(txn);
                    txn.commit();
                    value = Optional.of(id);
                }

            }

            return value;
        }, executionContext);
    }

    public CompletionStage<Map<String, String>> options() {
        return supplyAsync(() -> DB.find(Jogador.class).orderBy("nome").findList(), executionContext)
            .thenApply(list -> {
                HashMap<String, String> options = new LinkedHashMap<String, String>();
                for (Jogador j : list) {
                    options.put(j.getId().toString(), j.getNome());
                }
                return options;
            });
    }

    public Optional<Jogador> obterJogadorPorNomeSync(String nome) {

        return Optional.ofNullable(
            DB.find(Jogador.class)
                .where()
                .eq("nome", nome)
                .findOne()
        );

    }

    public Jogador insertSemIdSync(Jogador jogador) {
        DB.insert(jogador);
        return jogador;
    }

}