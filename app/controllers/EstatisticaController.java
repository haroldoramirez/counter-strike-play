package controllers;

import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import repositories.JogadorRepository;

import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.CompletionStage;

public class EstatisticaController extends Controller {

    private final FormFactory formFactory;
    private final JogadorRepository jogadorRepository;

    @Inject
    public EstatisticaController(FormFactory formFactory, JogadorRepository jogadorRepository) {
        this.formFactory = formFactory;
        this.jogadorRepository = jogadorRepository;
    }

    public CompletionStage<Result> telaInicio(Http.Request request) {

        DynamicForm listForm = formFactory.form().bindFromRequest(request);

        CompletionStage<Map<String, String>> optionsJogadores = jogadorRepository.options();

        return optionsJogadores.thenApply(options -> {
            return ok(views.html.estatisticas.inicio.render(listForm, options));
        });

    }

    public CompletionStage<Result> obterEstatisticaJogador(Http.Request request) {

        System.out.println("obterEstatisticaJogador");

        return null;

    }

}