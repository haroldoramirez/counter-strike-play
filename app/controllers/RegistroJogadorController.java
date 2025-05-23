package controllers;

import dtos.RegistroJogadorDTO;
import models.Jogador;
import models.RegistroJogador;
import models.enums.StatusPartida;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.libs.concurrent.ClassLoaderExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import repositories.JogadorRepository;
import repositories.MapaRepository;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;

public class RegistroJogadorController extends Controller {

    private final FormFactory formFactory;
    private final ClassLoaderExecutionContext classLoaderExecutionContext;
    private final JogadorRepository jogadorRepository;
    private final MapaRepository mapaRepository;

    @Inject
    public RegistroJogadorController(FormFactory formFactory, MessagesApi messagesApi, ClassLoaderExecutionContext classLoaderExecutionContext, JogadorRepository jogadorRepository, MapaRepository mapaRepository) {
        this.formFactory = formFactory;
        this.classLoaderExecutionContext = classLoaderExecutionContext;
        this.jogadorRepository = jogadorRepository;
        this.mapaRepository = mapaRepository;
    }

    public CompletionStage<Result> telaRegistroJogador(Http.Request request) {

        Form<RegistroJogadorDTO> registroJogadorDTOForm = formFactory.form(RegistroJogadorDTO.class);

        CompletionStage<Map<String, String>> jogadoresFuture = jogadorRepository.options();
        CompletionStage<Map<String, String>> mapasFuture = mapaRepository.options();
        Map<String, String> statusMap = optionsStatusPartida();

        return jogadoresFuture.thenCombineAsync(mapasFuture, (jogadores, mapas) -> {
            return ok(views.html.registrojogadores.cadastrar.render(
                    registroJogadorDTOForm,
                    jogadores,
                    mapas,
                    statusMap,
                    request
            ));
        }, classLoaderExecutionContext.current());
    }

    public CompletionStage<Result> inserirRegistroJogador(Http.Request request) {

        Form<RegistroJogadorDTO> registroJogadorDTOForm = formFactory.form(RegistroJogadorDTO.class).bindFromRequest(request);

        if (registroJogadorDTOForm.hasErrors()) {

            CompletionStage<Map<String, String>> jogadoresFuture = jogadorRepository.options();
            CompletionStage<Map<String, String>> mapasFuture = mapaRepository.options();
            Map<String, String> statusMap = optionsStatusPartida();

            return jogadoresFuture.thenCombineAsync(mapasFuture, (jogadores, mapas) ->
                badRequest(
                    views.html.registrojogadores.cadastrar.render(
                        registroJogadorDTOForm,
                        jogadores,
                        mapas,
                        statusMap,
                        request
                    )
                ),
            classLoaderExecutionContext.current()
            );

        } else {

            RegistroJogadorDTO registroJogadorDTO = registroJogadorDTOForm.get();

            RegistroJogador registroJogador;

            Calendar dataHoraCadastro = Calendar.getInstance();

            registroJogador = RegistroJogador.converterRegistroJogadorDTORegistroJogador(registroJogadorDTO);

            //TODO setor objeto jogador baseado no valor que veio do DTO
            registroJogador.setJogador(new Jogador());

            registroJogador.setDataCadastro(dataHoraCadastro);
            registroJogador.setDataAlteracao(dataHoraCadastro);

        }

        return null;

    }

    public static Map<String, String> optionsStatusPartida() {
        Map<String, String> options = new LinkedHashMap<>();
        for (StatusPartida status : StatusPartida.values()) {
            options.put(status.name(), status.getDescricao());
        }
        return options;
    }
    
}