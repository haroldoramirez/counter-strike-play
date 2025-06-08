package controllers;

import dtos.RegistroPartidaJogadorDTO;
import io.ebean.PagedList;
import jakarta.persistence.PersistenceException;
import models.Jogador;
import models.Mapa;
import models.RegistroPartidaJogador;
import models.enums.StatusPartida;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.libs.Files;
import play.libs.concurrent.ClassLoaderExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import repositories.JogadorRepository;
import repositories.MapaRepository;
import repositories.RegistroPartidaJogadorRepository;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class RegistroPartidaJogadorController extends Controller {

    private static final Logger log = LoggerFactory.getLogger(RegistroPartidaJogadorController.class);

    private static final String DUST_2 = "DUST 2";
    private static final Integer VITORIA = 1;
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

        DynamicForm listForm = formFactory.form().bindFromRequest(request);

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
                listForm,
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

    /**
     * salvar registros apartir de um arquivo CSV padronizado sincrono
     *
     * @param request   request
     */
    public Result salvarCSVSync(Http.Request request) {

        log.info("MÉTODO salvarCSV SINCRONO");

        Http.MultipartFormData<Files.TemporaryFile> body = request.body().asMultipartFormData();
        Http.MultipartFormData.FilePart<Files.TemporaryFile> csv = body.getFile("csv");

        if (csv == null) {
            return redirect(routes.RegistroPartidaJogadorController.listar(0, "qtdEliminacoes", "asc", ""))
                .flashing("error", "Selecione um arquivo válido.");
        }

        File file = csv.getRef().path().toFile();
        List<RegistroPartidaJogador> registros = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String linha;

            while ((linha = reader.readLine()) != null) {

                String[] campos = linha.split(";+");
                String nomeJogador = campos[0].trim().toUpperCase();

                Jogador novoJogador;

                Optional<Jogador> jogadorEncontrado = jogadorRepository.obterJogadorPorNomeSync(nomeJogador);

                if (jogadorEncontrado.isPresent()) {

                    novoJogador = jogadorEncontrado.get();

                } else {

                    novoJogador = new Jogador();

                    novoJogador.setNome(nomeJogador);

                    Calendar agora = Calendar.getInstance();
                    novoJogador.setDataCadastro(agora);
                    novoJogador.setDataAlteracao(agora);

                    novoJogador = jogadorRepository.insertSemIdSync(novoJogador);

                }

                Optional<Mapa> optMapa = mapaRepository.obterMapaPorNomeSync(DUST_2);

                if (optMapa.isEmpty()) {
                    return redirect(routes.RegistroPartidaJogadorController.listar(0, "qtdEliminacoes", "asc", ""))
                        .flashing("error", "Mapa não encontrado: " + DUST_2);
                }

                Mapa mapa = optMapa.get();

                RegistroPartidaJogador registro = new RegistroPartidaJogador();

                if (novoJogador == null) {
                    return redirect(routes.RegistroPartidaJogadorController.listar(0, "qtdEliminacoes", "asc", ""))
                        .flashing("error", "Ocorreum um erro ao salvar o jogador: " + novoJogador.getNome());
                }

                registro.setJogador(novoJogador);
                registro.setMapa(mapa);
                registro.setQtdEliminacoes(Integer.parseInt(campos[1]));
                registro.setQtdBaixas(Integer.parseInt(campos[2]));
                registro.setQtdDano(Integer.parseInt(campos[3]));
                registro.setPorcetagemHS(Integer.parseInt(campos[4]));

                if (Integer.parseInt(campos[5]) == VITORIA) {
                    registro.setStatusPartida(StatusPartida.VITORIA);
                } else {
                    registro.setStatusPartida(StatusPartida.DERROTA);
                }

                registro.setQtdDanoUtilitario(Integer.parseInt(campos[6]));
                registro.setQtdInimigosCegos(Integer.parseInt(campos[7]));

                Calendar agora = Calendar.getInstance();
                registro.setDataCadastro(agora);
                registro.setDataAlteracao(agora);

                registros.add(registro);

            }

            // Salva todos os registros de uma vez
            registroPartidaJogadorRepository.insertAllSync(registros);

            return redirect(routes.RegistroPartidaJogadorController.listar(0, "qtdEliminacoes", "asc", ""))
                .flashing("success", "Importação concluída com sucesso.");

        } catch (IOException e) {
            return redirect(routes.RegistroPartidaJogadorController.listar(0, "qtdEliminacoes", "asc", ""))
                .flashing("error", "Erro ao ler o arquivo: " + e.getMessage());
        } catch (Exception ex) {
            return redirect(routes.RegistroPartidaJogadorController.listar(0, "qtdEliminacoes", "asc", ""))
                .flashing("error", "Erro durante a importação: " + ex.getMessage());
        }

    }

    /**
     * salvar registros apartir de um arquivo CSV padronizado totalmente assicrono
     *
     * @param request   request
     */
    public CompletionStage<Result> salvarCSV(Http.Request request) {

        log.info("MÉTODO salvarCSV ASSINCRONO");

        Http.MultipartFormData<Files.TemporaryFile> body = request.body().asMultipartFormData();
        Http.MultipartFormData.FilePart<Files.TemporaryFile> csv = body.getFile("csv");

        if (csv == null) {
            return CompletableFuture.completedFuture(
                redirect(routes.RegistroPartidaJogadorController.listar(0, "qtdEliminacoes", "asc", ""))
                    .flashing("error", "Selecione um arquivo válido.")
            );
        }

        File file = csv.getRef().path().toFile();
        Map<String, CompletionStage<Jogador>> jogadorCache = new ConcurrentHashMap<>();
        List<CompletionStage<RegistroPartidaJogador>> promessas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String linha;

            while ((linha = reader.readLine()) != null) {

                String[] campos = linha.split(";+");
                String nomeJogador = campos[0].trim().toUpperCase();

                CompletionStage<Jogador> jogadorFuture = jogadorCache.computeIfAbsent(nomeJogador, nome -> {

                    return jogadorRepository.obterJogadorPorNome(nome).thenCompose(optJogador -> {

                        if (optJogador.isPresent()) {
                            return CompletableFuture.completedFuture(optJogador.get());
                        }

                        Jogador novo = new Jogador();
                        novo.setNome(nome);
                        Calendar agora = Calendar.getInstance();
                        novo.setDataCadastro(agora);
                        novo.setDataAlteracao(agora);

                        return jogadorRepository.insertSemId(novo)
                            .exceptionallyCompose(ex -> {

                                if (ex.getCause() instanceof PersistenceException &&
                                        ex.getCause().getMessage().contains("23505")) {

                                    // Condição de corrida: outro thread inseriu antes
                                    return jogadorRepository.obterJogadorPorNome(nome)
                                    .thenApply(jOpt -> jOpt.orElseThrow(() ->
                                        new CompletionException(new RuntimeException("Falha ao recuperar jogador existente."))));

                                } else {
                                    throw new CompletionException(ex);
                                }

                            });
                    });
                });

                CompletionStage<RegistroPartidaJogador> promessa = jogadorFuture.thenCombineAsync(
                    mapaRepository.obterMapaPorNome(DUST_2),
                    (jogador, optMapa) -> {

                        if (optMapa.isEmpty()) {
                            throw new CompletionException(new NoSuchElementException("Mapa não encontrado: " + DUST_2));
                        }

                        Mapa mapa = optMapa.get();

                        RegistroPartidaJogador registro = new RegistroPartidaJogador();
                        registro.setJogador(jogador);
                        registro.setMapa(mapa);
                        registro.setQtdEliminacoes(Integer.parseInt(campos[1]));
                        registro.setQtdBaixas(Integer.parseInt(campos[2]));
                        registro.setQtdDano(Integer.parseInt(campos[3]));
                        registro.setPorcetagemHS(Integer.parseInt(campos[4]));

                        if (Integer.parseInt(campos[5]) == VITORIA) {
                            registro.setStatusPartida(StatusPartida.VITORIA);
                        } else {
                            registro.setStatusPartida(StatusPartida.DERROTA);
                        }

                        registro.setQtdDanoUtilitario(Integer.parseInt(campos[6]));
                        registro.setQtdInimigosCegos(Integer.parseInt(campos[7]));

                        Calendar agora = Calendar.getInstance();
                        registro.setDataCadastro(agora);
                        registro.setDataAlteracao(agora);

                        return registro;
                    }
                );

                promessas.add(promessa);
            }

            return CompletableFuture
                .allOf(promessas.stream().map(CompletionStage::toCompletableFuture).toArray(CompletableFuture[]::new))
                .thenApply(v ->
                    promessas.stream()
                        .map(CompletionStage::toCompletableFuture)
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList())
                )
                .thenCompose(registros ->
                    registroPartidaJogadorRepository.insertAll(registros)
                        .thenApply(ok ->
                            redirect(routes.RegistroPartidaJogadorController.listar(0, "qtdEliminacoes", "asc", ""))
                                .flashing("success", "Importação concluída com sucesso.")
                        )
                )
                .exceptionally(ex ->
                    redirect(routes.RegistroPartidaJogadorController.listar(0, "qtdEliminacoes", "asc", ""))
                        .flashing("error", "Erro durante a importação: " + ex.getCause().getMessage())
                );

        } catch (IOException e) {
            return CompletableFuture.completedFuture(
                redirect(routes.RegistroPartidaJogadorController.listar(0, "qtdEliminacoes", "asc", ""))
                    .flashing("error", "Erro ao ler o arquivo: " + e.getMessage())
            );
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