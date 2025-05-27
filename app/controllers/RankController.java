package controllers;

import dtos.RankJogadorDTO;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.libs.concurrent.ClassLoaderExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import repositories.JogadorRepository;

import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.CompletionStage;

public class RankController extends Controller {

    private final FormFactory formFactory;
    private final ClassLoaderExecutionContext classLoaderExecutionContext;
    private final JogadorRepository jogadorRepository;

    @Inject
    public RankController(FormFactory formFactory, ClassLoaderExecutionContext classLoaderExecutionContext, JogadorRepository jogadorRepository) {
        this.formFactory = formFactory;
        this.classLoaderExecutionContext = classLoaderExecutionContext;
        this.jogadorRepository = jogadorRepository;
    }

    public CompletionStage<Result> telaInicio(Http.Request request) {

        DynamicForm listForm = formFactory.form().bindFromRequest(request);

        CompletionStage<Map<String, String>> optionsJogadores = jogadorRepository.options();

        RankJogadorDTO rankJogadorDTO = new RankJogadorDTO();

        return optionsJogadores.thenApply(options -> {
            return ok(views.html.rank.inicio.render(listForm, options, rankJogadorDTO, request));
        });

    }

}
