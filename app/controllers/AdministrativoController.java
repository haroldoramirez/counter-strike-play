package controllers;

import play.libs.concurrent.ClassLoaderExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import repositories.AdministrativoRepository;

import javax.inject.Inject;

public class AdministrativoController extends Controller {

    private final ClassLoaderExecutionContext classLoaderExecutionContext;
    private final AdministrativoRepository administrativoRepository;

    @Inject
    public AdministrativoController(ClassLoaderExecutionContext classLoaderExecutionContext, AdministrativoRepository administrativoRepository) {
        this.classLoaderExecutionContext = classLoaderExecutionContext;
        this.administrativoRepository = administrativoRepository;
    }

    public Result limparBancoH2(Http.Request request) {

        administrativoRepository.limparBancoH2(request);

        return redirect(routes.HomeController.inicio()).flashing("success", "Banco de dados limpo com sucesso!");

    }

}