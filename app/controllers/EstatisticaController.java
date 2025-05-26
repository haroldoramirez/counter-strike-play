package controllers;

import dtos.EstatisticaJogadorDTO;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.libs.concurrent.ClassLoaderExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import repositories.EstatisticaRepository;
import repositories.JogadorRepository;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class EstatisticaController extends Controller {

    private final FormFactory formFactory;
    private final JogadorRepository jogadorRepository;
    private final EstatisticaRepository estatisticaRepository;
    private final ClassLoaderExecutionContext classLoaderExecutionContext;

    @Inject
    public EstatisticaController(FormFactory formFactory, JogadorRepository jogadorRepository, EstatisticaRepository estatisticaRepository, ClassLoaderExecutionContext classLoaderExecutionContext) {
        this.formFactory = formFactory;
        this.jogadorRepository = jogadorRepository;
        this.estatisticaRepository = estatisticaRepository;
        this.classLoaderExecutionContext = classLoaderExecutionContext;
    }

    public CompletionStage<Result> telaInicio(Http.Request request) {

        DynamicForm listForm = formFactory.form().bindFromRequest(request);

        CompletionStage<Map<String, String>> optionsJogadores = jogadorRepository.options();

        EstatisticaJogadorDTO estatisticaJogadorDTO = new EstatisticaJogadorDTO();

        return optionsJogadores.thenApply(options -> {
            return ok(views.html.estatisticas.inicio.render(listForm, options, estatisticaJogadorDTO));
        });

    }

    public CompletionStage<Result> obterEstatisticaJogador(Http.Request request) {

        DynamicForm listForm = formFactory.form().bindFromRequest(request);
        String[] filtroJogador = request.queryString().get("filterJogador");

        // Verifica se o parâmetro está presente e é válido
        if (filtroJogador == null || filtroJogador.length == 0 || filtroJogador[0].isEmpty()) {
            return CompletableFuture.completedFuture(
                    badRequest("ID do jogador não informado.")
            );
        }

        Long jogadorId;

        try {
            jogadorId = Long.valueOf(filtroJogador[0]);
        } catch (NumberFormatException e) {
            return CompletableFuture.completedFuture(
                    badRequest("ID do jogador inválido.")
            );
        }

        // Obter opções e estatísticas de forma assíncrona
        CompletionStage<Map<String, String>> optionsJogadores = jogadorRepository.options();
        CompletionStage<EstatisticaJogadorDTO> estatisticas = estatisticaRepository.gerarEstatisticas(jogadorId);

        return optionsJogadores.thenCombineAsync(estatisticas, (options, estatisticaJogadorDTO) -> {
            return ok(views.html.estatisticas.inicio.render(listForm, options, estatisticaJogadorDTO));
        }, classLoaderExecutionContext.current());
    }

}