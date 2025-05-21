package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class EstatisticaController extends Controller {

    public Result telaInicio() {
        return ok(views.html.estatisticas.inicio.render());
    }
}