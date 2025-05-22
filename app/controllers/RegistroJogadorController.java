package controllers;

import dtos.RegistroJogadorDTO;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.libs.concurrent.ClassLoaderExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import repositories.JogadorRepository;

import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class RegistroJogadorController extends Controller {

    private final FormFactory formFactory;
    private final ClassLoaderExecutionContext classLoaderExecutionContext;
    private final JogadorRepository jogadorRepository;

    @Inject
    public RegistroJogadorController(FormFactory formFactory, MessagesApi messagesApi, ClassLoaderExecutionContext classLoaderExecutionContext, JogadorRepository jogadorRepository) {
        this.formFactory = formFactory;
        this.classLoaderExecutionContext = classLoaderExecutionContext;
        this.jogadorRepository = jogadorRepository;
    }

    public CompletionStage<Result> telaRegistroJogador(Http.Request request) {

        Form<RegistroJogadorDTO> registroJogadorDTOForm = formFactory.form(RegistroJogadorDTO.class);

        return jogadorRepository.options().thenApplyAsync((Map<String, String> jogadores) -> {

            return ok(views.html.registrojogadores.cadastrar.render(registroJogadorDTOForm, jogadores, request));
        }, classLoaderExecutionContext.current());

    }

    public CompletionStage<Result> inserirRegistroJogador(Http.Request request) {

        Form<RegistroJogadorDTO> registroJogadorDTOForm = formFactory.form(RegistroJogadorDTO.class).bindFromRequest(request);

        if (registroJogadorDTOForm.hasErrors()) {

            return jogadorRepository.options().thenApplyAsync((Map<String, String> jogadores) -> {

                return badRequest(views.html.registrojogadores.cadastrar.render(registroJogadorDTOForm, jogadores, request));
            }, classLoaderExecutionContext.current());

        }

        return null;

    }

}