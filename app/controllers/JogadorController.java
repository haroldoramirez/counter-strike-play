package controllers;

import dtos.JogadorDTO;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;

public class JogadorController extends Controller {

    private final FormFactory formFactory;

    @Inject
    public JogadorController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    public Result telaCadastroJogador(Http.Request request) {
        Form<JogadorDTO> jogadorDTO = formFactory.form(JogadorDTO.class);
        return ok(views.html.jogadores.cadastrar.render(jogadorDTO, request));
    }

    public Result telaListaJogador() {
        return ok(views.html.jogadores.listar.render());
    }

    public Result inserirJogador(Http.Request request) {

        //Resgata os dados do formulario atraves de uma requisicao e realiza a validacao dos campos
        Form<JogadorDTO> jogadorDTO = formFactory.form(JogadorDTO.class).bindFromRequest(request);

        //se existir erros nos campos do formulario retorne o CursoFormData com os erros
        if (jogadorDTO.hasErrors()) {
            return badRequest(views.html.jogadores.cadastrar.render(jogadorDTO, request));
        }

        return ok("Jogador inserido com sucesso!");
    }

}
