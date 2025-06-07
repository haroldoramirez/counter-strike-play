package repositories;

import io.ebean.DB;
import play.mvc.Http;

import javax.inject.Inject;

public class AdministrativoRepository {

    private final DatabaseExecutionContext executionContext;

    @Inject
    public AdministrativoRepository(DatabaseExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    public void limparBancoH2(Http.Request request) {

        DB.sqlUpdate("DELETE FROM registro_partida_jogador").execute();
        DB.sqlUpdate("DELETE FROM jogador").execute();

    }

}