package controllers;

import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import repositories.AdministrativoRepository;

import javax.inject.Inject;

public class AdministrativoController extends Controller {

    private final AdministrativoRepository administrativoRepository;

    @Inject
    public AdministrativoController(AdministrativoRepository administrativoRepository) {
        this.administrativoRepository = administrativoRepository;
    }

    public Result limparBanco(Http.Request request) {

        administrativoRepository.limparBanco(request);

        return redirect(routes.HomeController.inicio()).flashing("success", "Banco de dados limpo com sucesso!");

    }

}