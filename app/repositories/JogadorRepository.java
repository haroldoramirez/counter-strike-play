package repositories;

import io.ebean.DB;
import models.Jogador;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class JogadorRepository {

    private final DatabaseExecutionContext executionContext;

    @Inject
    public JogadorRepository(DatabaseExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    public CompletionStage<Long> insert(Jogador jogador) {
        return supplyAsync(() -> {
            DB.insert(jogador);
            return jogador.getId();
        }, executionContext);
    }

}
