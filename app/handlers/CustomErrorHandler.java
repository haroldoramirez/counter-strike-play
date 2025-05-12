package handlers;

import play.http.HttpErrorHandler;
import play.mvc.Http;
import play.mvc.Result;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static play.mvc.Results.*;

public class CustomErrorHandler implements HttpErrorHandler {

    @Override
    public CompletionStage<Result> onClientError(Http.RequestHeader request, int statusCode, String message) {
        if (statusCode == 404) {
            return CompletableFuture.completedFuture(
                    notFound("Erro 404 - rota não encontrada para: " + request.uri())
            );
        } else {
            return CompletableFuture.completedFuture(
                    status(statusCode, "Erro do cliente: " + message)
            );
        }
    }

    @Override
    public CompletionStage<Result> onServerError(Http.RequestHeader request, Throwable exception) {
        return CompletableFuture.completedFuture(
                internalServerError("Erro interno do servidor: " + exception.getMessage())
        );
    }
}
