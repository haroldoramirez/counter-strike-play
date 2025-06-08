package repositories;

import io.ebean.*;
import models.Jogador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class JogadorRepository {

    private static final Logger log = LoggerFactory.getLogger(JogadorRepository.class);

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
                    .ilike("nome", nome)
                    .findOne()
            ), executionContext
        );
    }

    /**
     * Retorna um objeto pelo nome realizando uma query nativa
     *
     * @param nome Nome do objeto a buscar
     */
    public CompletionStage<Optional<Jogador>> obterJogadorPorNomeNativo(String nome) {
        return CompletableFuture.supplyAsync(() -> {

            String sql = "SELECT * FROM jogador WHERE nome LIKE :nome LIMIT 1";

            SqlQuery query = DB.sqlQuery(sql);
            query.setParameter("nome", "%" + nome + "%");

            List<SqlRow> rows = query.findList();

            if (rows.isEmpty()) {
                return Optional.empty();
            }

            SqlRow row = rows.get(0);

            Jogador jogador = new Jogador();
            jogador.setId(row.getLong("id"));
            jogador.setNome(row.getString("nome"));

            Timestamp dataCadastro = row.getTimestamp("data_cadastro");
            Timestamp dataAlteracao = row.getTimestamp("data_alteracao");

            if (dataCadastro != null) {
                Calendar calCadastro = Calendar.getInstance();
                calCadastro.setTimeInMillis(dataCadastro.getTime());
                jogador.setDataCadastro(calCadastro);
            }

            if (dataAlteracao != null) {
                Calendar calAlteracao = Calendar.getInstance();
                calAlteracao.setTimeInMillis(dataAlteracao.getTime());
                jogador.setDataAlteracao(calAlteracao);
            }

            return Optional.of(jogador);
        }, executionContext);
    }

    /**
     * Retorna uma lista paginada de Objetos
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
     * Salva na base de dados um novo Objeto
     *
     * @param jogador Objeto ja validado retorna apenas o Id
     */
    public CompletionStage<Jogador> insert(Jogador jogador) {
        return supplyAsync(() -> {
            DB.insert(jogador);
            return jogador;
        }, executionContext);
    }

    /**
     * Salva na base de dados um novo Objeto controlando a transaction do banco
     *
     * @param jogador Objeto ja validado retorna apenas o Id
     */
    public CompletionStage<Jogador> salvarComTransacao(Jogador jogador) {
        return CompletableFuture.supplyAsync(() -> {
            try (Transaction txn = DB.beginTransaction()) {
                DB.insert(jogador);
                txn.commit();
                return jogador;
            } catch (Exception e) {
                log.error("Ocorreu um erro ao salvar Jogador: {}", e.getMessage());
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

    /**
     * Metodo com o objetivo de criar uma options de objetos que sera carregada em tela
     *
     */
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

}