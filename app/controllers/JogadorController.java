package controllers;

import dtos.JogadorDTO;
import models.Jogador;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.libs.concurrent.ClassLoaderExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import repositories.JogadorRepository;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class JogadorController extends Controller {

    private final MessagesApi messagesApi;
    private final JogadorRepository jogadorRepository;
    private final FormFactory formFactory;
    private final ClassLoaderExecutionContext classLoaderExecutionContext;

    @Inject
    public JogadorController(FormFactory formFactory, JogadorRepository jogadorRepository, MessagesApi messagesApi, ClassLoaderExecutionContext classLoaderExecutionContext) {
        this.formFactory = formFactory;
        this.jogadorRepository = jogadorRepository;
        this.messagesApi = messagesApi;
        this.classLoaderExecutionContext = classLoaderExecutionContext;
    }

    /**
     * Carregar lista paginada de jogadores
     *
     * @param page   Current page number (starts from 0)
     * @param sortBy Column to be sorted
     * @param filter Filter applied on computer names
     */
    public CompletionStage<Result> listar(Http.Request request, int page, String sortBy, String order, String filter) {

        // Run a db operation in another thread (using DatabaseExecutionContext)
        return jogadorRepository.page(page, 10, sortBy, order, filter).thenApplyAsync(list -> {
            // This is the HTTP rendering thread context
            return ok(views.html.jogadores.listar.render(list, sortBy, order, filter, request, messagesApi.preferred(request)));
        }, classLoaderExecutionContext.current());

    }

    /**
     * Exibe o formulário para cadastrar um jogador existente
     *
     * @param request requisicao
     */
    public Result telaCadastrarJogador(Http.Request request) {
        Form<JogadorDTO> jogadorDTOForm = formFactory.form(JogadorDTO.class);
        return ok(views.html.jogadores.cadastrar.render(jogadorDTOForm, request));
    }

    /**
     * Exibe o formulário para editar um jogador existente
     *
     * @param request requisicao
     * @param id do jogador a ser editado
     */
    public CompletionStage<Result> telaEditarJogador(Http.Request request, Long id) {
        return jogadorRepository.obterJogadorById(id).thenApplyAsync(jogadorOptional -> {

            if (jogadorOptional.isEmpty()) {
                return notFound("Jogador não encontrado.");
            }

            Jogador jogador = jogadorOptional.get();

            // Converte o Jogador para a entidade JogadorDTO
            JogadorDTO jogadorDTO = JogadorDTO.converterJogadorDTO(jogador);

            Form<JogadorDTO> jogadorDTOForm = formFactory.form(JogadorDTO.class).fill(jogadorDTO);

            return ok(views.html.jogadores.editar.render(
                    id,
                    jogadorDTOForm,
                    request
            ));

        }, classLoaderExecutionContext.current());
    }

    /**
     * Editar um jogador existente apartir do id
     *
     * @param request requisicao
     * @param id do jogador a ser editado
     */
    public CompletionStage<Result> editarJogador(Http.Request request, Long id) throws ParseException {

        // Resgata os dados do formulário através de uma requisição e realiza a validação dos campos
        Form<JogadorDTO> jogadorDTOForm = formFactory.form(JogadorDTO.class).bindFromRequest(request);

        if (jogadorDTOForm.hasErrors()) {
            // Apenas renderiza o formulário com os erros
            return CompletableFuture.completedFuture(
                    badRequest(views.html.jogadores.editar.render(
                            id,
                            jogadorDTOForm,
                            request
                    ))
            );
        } else {

            JogadorDTO dto = jogadorDTOForm.get();

            // Converte o DTO para a entidade Jogador
            Jogador jogador = Jogador.converterDTOJogador(dto);

            // Insere o jogador no banco e redireciona
            return jogadorRepository.update(id, jogador).thenApplyAsync(data ->
                            redirect(routes.JogadorController.listar(0, "nome", "asc", ""))
                                    .flashing("success", "Jogador '" + jogador.getNome() + "' foi alterado com sucesso!"),
                    classLoaderExecutionContext.current()
            );
        }
    }

    /**
     * Remove um jogador existente apartir do id
     *
     * @param request requisicao
     * @param id do jogador a ser editado
     */
    public CompletionStage<Result> removerJogador(Http.Request request, Long id) {
        return null;
    }

    /**
     * Salva um jogador na base de dados
     *
     * @param request requisicao
     */
    public CompletionStage<Result> inserirJogador(Http.Request request) throws ParseException {

        // Resgata os dados do formulário através de uma requisição e realiza a validação dos campos
        Form<JogadorDTO> jogadorDTOForm = formFactory.form(JogadorDTO.class).bindFromRequest(request);

        // Se existir erros nos campos do formulário, retorna com os erros
        if (jogadorDTOForm.hasErrors()) {
            return CompletableFuture.completedFuture(
                badRequest(views.html.jogadores.cadastrar.render(
                    jogadorDTOForm,
                    request
                ))
            );
        }

        JogadorDTO dto = jogadorDTOForm.get();

        // Converte o DTO para a entidade Jogador
        Jogador jogador = Jogador.converterDTOJogador(dto);

        // Insere o jogador no banco e redireciona
        return jogadorRepository.insert(jogador).thenApplyAsync(data ->
                        redirect(routes.JogadorController.listar(0, "nome", "asc", ""))
                                .flashing("success", "Jogador '" + jogador.getNome() + "' foi criado com sucesso!"),
                classLoaderExecutionContext.current()
        );
    }

}