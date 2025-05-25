package controllers;

import dtos.RegistroPartidaJogadorDTO;
import io.ebean.PagedList;
import models.Jogador;
import models.Mapa;
import models.RegistroPartidaJogador;
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
import repositories.RegistroPartidaJogadorRepository;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class RegistroPartidaJogadorController extends Controller {

    private final MessagesApi messagesApi;
    private final FormFactory formFactory;
    private final ClassLoaderExecutionContext classLoaderExecutionContext;
    private final JogadorRepository jogadorRepository;
    private final MapaRepository mapaRepository;
    private final RegistroPartidaJogadorRepository registroPartidaJogadorRepository;

    @Inject
    public RegistroPartidaJogadorController(MessagesApi messagesApi, FormFactory formFactory, ClassLoaderExecutionContext classLoaderExecutionContext, JogadorRepository jogadorRepository, MapaRepository mapaRepository, RegistroPartidaJogadorRepository registroPartidaJogadorRepository) {
        this.messagesApi = messagesApi;
        this.formFactory = formFactory;
        this.classLoaderExecutionContext = classLoaderExecutionContext;
        this.jogadorRepository = jogadorRepository;
        this.mapaRepository = mapaRepository;
        this.registroPartidaJogadorRepository = registroPartidaJogadorRepository;
    }

    /**
     * Carregar lista paginada de objetos
     *
     * @param page   Current page number (starts from 0)
     * @param sortBy Column to be sorted
     * @param filter Filter applied on computer names
     */
    public CompletionStage<Result> listar(Http.Request request, int page, String sortBy, String order, String filter) {

        String[] filtroJogador = request.queryString().get("filterJogador");

        if (filtroJogador != null && filtroJogador.length > 0) {
            filter = Arrays.stream(filtroJogador).findFirst().get();
        }

        CompletionStage<Map<String, String>> optionsJogadores = jogadorRepository.options();
        CompletionStage<PagedList<RegistroPartidaJogador>> registrosPartidaJogador = registroPartidaJogadorRepository.page(page, 10, sortBy, order, filter);

        String finalFilter = filter;

        return optionsJogadores.thenCombineAsync(registrosPartidaJogador, (jogadoresMap, paginaRegistrosJogadores) ->
            ok(views.html.registropartidajogadores.listar.render(
                paginaRegistrosJogadores,
                jogadoresMap,
                sortBy,
                order,
                finalFilter,
                request,
                messagesApi.preferred(request)
            )),
            classLoaderExecutionContext.current()
        );

    }

    public CompletionStage<Result> telaRegistroPartidaJogador(Http.Request request) {

        Form<RegistroPartidaJogadorDTO> registroJogadorDTOForm = formFactory.form(RegistroPartidaJogadorDTO.class);

        CompletionStage<Map<String, String>> jogadoresFuture = jogadorRepository.options();
        CompletionStage<Map<String, String>> mapasFuture = mapaRepository.options();
        Map<String, String> statusMap = optionsStatusPartida();

        return jogadoresFuture.thenCombineAsync(mapasFuture, (jogadores, mapas) -> {
            return ok(views.html.registropartidajogadores.cadastrar.render(
                    registroJogadorDTOForm,
                    jogadores,
                    mapas,
                    statusMap,
                    request
            ));
        }, classLoaderExecutionContext.current());
    }

    public CompletionStage<Result> inserirRegistroPartidaJogador(Http.Request request) {

        Form<RegistroPartidaJogadorDTO> registroJogadorDTOForm = formFactory.form(RegistroPartidaJogadorDTO.class).bindFromRequest(request);

        if (registroJogadorDTOForm.hasErrors()) {

            CompletionStage<Map<String, String>> jogadoresFuture = jogadorRepository.options();
            CompletionStage<Map<String, String>> mapasFuture = mapaRepository.options();
            Map<String, String> statusPartida = optionsStatusPartida();

            return jogadoresFuture.thenCombineAsync(mapasFuture, (jogadores, mapas) ->
                badRequest(
                    views.html.registropartidajogadores.cadastrar.render(
                            registroJogadorDTOForm,
                            jogadores,
                            mapas,
                            statusPartida,
                            request
                    )
                ),
            classLoaderExecutionContext.current());

        } else {

            RegistroPartidaJogadorDTO registroJogadorDTO = registroJogadorDTOForm.get();

            RegistroPartidaJogador registroJogador = RegistroPartidaJogador.converterRegistroJogadorDTORegistroJogador(registroJogadorDTO);

            CompletionStage<Optional<Jogador>> jogadorSelecionado = jogadorRepository.obterJogadorById(registroJogadorDTO.getJogador());
            CompletionStage<Optional<Mapa>> mapaSelecionado = mapaRepository.obterMapaById(registroJogadorDTO.getMapa());

            return jogadorSelecionado.thenCombineAsync(mapaSelecionado, (jogadorOpt, mapaOpt) -> {

                Optional<RegistroPartidaJogador> registroOpt;

                if (jogadorOpt.isPresent() && mapaOpt.isPresent()) {

                    registroJogador.setJogador(jogadorOpt.get());
                    registroJogador.setMapa(mapaOpt.get());

                    Calendar agora = Calendar.getInstance();
                    registroJogador.setDataCadastro(agora);
                    registroJogador.setDataAlteracao(agora);

                    registroOpt = Optional.of(registroJogador);

                } else {
                    registroOpt = Optional.empty();
                }

                return registroOpt;

            }, classLoaderExecutionContext.current()).thenComposeAsync((Optional<RegistroPartidaJogador> registroOpt) -> {

                if (registroOpt.isPresent()) {

                    return registroPartidaJogadorRepository.insert(registroOpt.get()).thenApply(result ->
                        redirect(routes.RegistroPartidaJogadorController.listar(0, "qtdEliminacoes", "asc", ""))
                            .flashing("success", "Registro de partida para o Jogador " + registroOpt.get().getJogador().getNome() + " cadastrado com sucesso!")
                    );

                } else {

                    return CompletableFuture.completedFuture(
                        badRequest("Jogador ou mapa não encontrados.")
                    );

                }

            }, classLoaderExecutionContext.current());
        }
    }

    public static Map<String, String> optionsStatusPartida() {
        Map<String, String> options = new LinkedHashMap<>();
        for (StatusPartida status : StatusPartida.values()) {
            options.put(status.name(), status.getDescricao());
        }
        return options;
    }

}