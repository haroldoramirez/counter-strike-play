package controllers;

import dtos.RankJogadorDTO;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.libs.concurrent.ClassLoaderExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import repositories.JogadorRepository;
import repositories.RankRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;

public class RankController extends Controller {

    private final FormFactory formFactory;
    private final ClassLoaderExecutionContext classLoaderExecutionContext;
    private final JogadorRepository jogadorRepository;
    private final RankRepository rankRepository;

    @Inject
    public RankController(FormFactory formFactory, ClassLoaderExecutionContext classLoaderExecutionContext, JogadorRepository jogadorRepository, RankRepository rankRepository) {
        this.formFactory = formFactory;
        this.classLoaderExecutionContext = classLoaderExecutionContext;
        this.jogadorRepository = jogadorRepository;
        this.rankRepository = rankRepository;
    }

    public CompletionStage<Result> telaInicio(Http.Request request) {

        // Apenas obter o rank dos jogadores
        CompletionStage<List<RankJogadorDTO>> rankGeralJogadores = rankRepository.gerarRankJogadores();

        return rankGeralJogadores.thenApplyAsync(rankJogadorDTOs -> {
            return ok(views.html.rank.inicio.render(rankJogadorDTOs, request));
        }, classLoaderExecutionContext.current());

    }

}
