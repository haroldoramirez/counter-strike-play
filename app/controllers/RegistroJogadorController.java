package controllers;

import dtos.RegistroJogadorDTO;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.libs.concurrent.ClassLoaderExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;

public class RegistroJogadorController extends Controller {

    private final MessagesApi messagesApi;
    private final FormFactory formFactory;
    private final ClassLoaderExecutionContext classLoaderExecutionContext;

    @Inject
    public RegistroJogadorController(FormFactory formFactory, MessagesApi messagesApi, ClassLoaderExecutionContext classLoaderExecutionContext) {
        this.formFactory = formFactory;
        this.messagesApi = messagesApi;
        this.classLoaderExecutionContext = classLoaderExecutionContext;
    }

    public Result telaRegistroJogador(Http.Request request) {
        Form<RegistroJogadorDTO> registroJogadorDTOForm = formFactory.form(RegistroJogadorDTO.class);
        return ok(views.html.registrojogadores.cadastrar.render(registroJogadorDTOForm, request));
    }

}