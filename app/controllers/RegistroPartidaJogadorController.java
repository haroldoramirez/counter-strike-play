package controllers;

import dtos.RegistroPartidaJogadorDTO;
import io.ebean.PagedList;
import jakarta.persistence.PersistenceException;
import models.Jogador;
import models.Mapa;
import models.RegistroPartidaJogador;
import models.enums.StatusPartida;
import org.springframework.util.StringUtils;
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
import java.util.stream.Collectors;

public class RegistroPartidaJogadorController extends Controller {

    private static final String DUST_2 = "Dust 2";
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
     * salvar registros apartir de um arquivo CSV padronizado totalmente assicrono
     *
     * @param request   request
     */
    public CompletionStage<Result> salvarCSV(Http.Request request) {

        Http.MultipartFormData<Files.TemporaryFile> body = request.body().asMultipartFormData();
        Http.MultipartFormData.FilePart<Files.TemporaryFile> csv = body.getFile("csv");

        if (csv == null) {
            return CompletableFuture.completedFuture(
                    redirect(routes.RegistroPartidaJogadorController.listar(0, "qtdEliminacoes", "asc", ""))
                            .flashing("error", "Selecione um arquivo válido.")
            );
        }

        File file = csv.getRef().path().toFile();
        List<CompletionStage<RegistroPartidaJogador>> promessasRegistroJogador = new ArrayList<>();

        // Cache local para evitar criar o mesmo jogador mais de uma vez
        Map<String, CompletionStage<Jogador>> cacheJogadores = new HashMap<>();
        Set<String> nomesProcessados = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String linha;

            while ((linha = reader.readLine()) != null) {

                String[] campos = linha.split(";+");

                if (campos.length < 8) continue; // ignora linhas incompletas

                String nomeJogadorOriginal = campos[0].trim();
                String nomeJogadorNormalizado = nomeJogadorOriginal.toLowerCase();

                // Evita processar nomes duplicados no mesmo CSV
                if (!nomesProcessados.add(nomeJogadorNormalizado)) {
                    continue;
                }

                CompletionStage<Jogador> jogadorFuture = cacheJogadores.computeIfAbsent(nomeJogadorNormalizado, nome ->
                        jogadorRepository.obterJogadorPorNome(nome).thenCompose(optJogador -> {

                            if (optJogador.isPresent()) {
                                return CompletableFuture.completedFuture(optJogador.get());
                            }

                            Jogador novoJogador = new Jogador();
                            novoJogador.setNome(nomeJogadorOriginal); // Salva com o nome original
                            Calendar agora = Calendar.getInstance();
                            novoJogador.setDataCadastro(agora);
                            novoJogador.setDataAlteracao(agora);

                            return jogadorRepository.insertSemId(novoJogador)
                                    .exceptionallyCompose(ex -> {
                                        if (ex.getCause() instanceof PersistenceException && ex.getCause().getMessage().contains("23505")) {
                                            return jogadorRepository.obterJogadorPorNome(nome)
                                                    .thenApply(jogadorOpt -> jogadorOpt.orElseThrow(() ->
                                                            new CompletionException(new RuntimeException("Erro de duplicidade contornado."))));
                                        } else {
                                            throw new CompletionException(ex);
                                        }
                                    });

                        })
                );

                CompletionStage<RegistroPartidaJogador> promessaRegistro = jogadorFuture.thenCombineAsync(
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

                            if (Integer.parseInt(campos[5]) == 1) {
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

                promessasRegistroJogador.add(promessaRegistro);
            }

            return CompletableFuture
                    .allOf(promessasRegistroJogador.stream()
                            .map(CompletionStage::toCompletableFuture)
                            .toArray(CompletableFuture[]::new))
                    .thenApply(v ->
                            promessasRegistroJogador.stream()
                                    .map(CompletionStage::toCompletableFuture)
                                    .map(CompletableFuture::join)
                                    .collect(Collectors.toList())
                    )
                    .thenCompose(listaFinal ->
                            registroPartidaJogadorRepository.insertAll(listaFinal)
                                    .thenApply(v ->
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