package controllers;

import dtos.MapaDTO;
import models.Mapa;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.libs.concurrent.ClassLoaderExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import repositories.MapaRepository;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.Calendar;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class MapaController extends Controller {

    private final MessagesApi messagesApi;
    private final MapaRepository mapaRepository;
    private final FormFactory formFactory;
    private final ClassLoaderExecutionContext classLoaderExecutionContext;

    @Inject
    public MapaController(FormFactory formFactory, MapaRepository mapaRepository, MessagesApi messagesApi, ClassLoaderExecutionContext classLoaderExecutionContext) {
        this.formFactory = formFactory;
        this.mapaRepository = mapaRepository;
        this.messagesApi = messagesApi;
        this.classLoaderExecutionContext = classLoaderExecutionContext;
    }

    /**
     * Carregar lista paginada de mapa
     *
     * @param page   Current page number (starts from 0)
     * @param sortBy Column to be sorted
     * @param filter Filter applied on computer names
     */
    public CompletionStage<Result> listar(Http.Request request, int page, String sortBy, String order, String filter) {

        // Run a db operation in another thread (using DatabaseExecutionContext)
        return mapaRepository.page(page, 10, sortBy, order, filter).thenApplyAsync(list -> {
            // This is the HTTP rendering thread context
            return ok(views.html.mapas.listar.render(list, sortBy, order, filter, request, messagesApi.preferred(request)));
        }, classLoaderExecutionContext.current());

    }

    /**
     * Exibe o formulário para cadastrar um mapa existente
     *
     * @param request requisicao
     */
    public Result telaCadastrar(Http.Request request) {
        Form<MapaDTO> mapaDTOForm = formFactory.form(MapaDTO.class);
        return ok(views.html.mapas.cadastrar.render(mapaDTOForm, request));
    }

    /**
     * Exibe o formulário para editar um mapa existente
     *
     * @param request requisicao
     * @param id do mapa a ser editado
     */
    public CompletionStage<Result> telaEditar(Http.Request request, Long id) {
        return mapaRepository.obterMapaById(id).thenApplyAsync(mapaOptional -> {

            if (mapaOptional.isEmpty()) {
                return notFound("Mapa não encontrado.");
            }

            Mapa mapa = mapaOptional.get();

            // Converte o Mapa para a entidade MapaDTO
            MapaDTO mapaDTO = MapaDTO.converterMapaDTO(mapa);

            Form<MapaDTO> mapaDTOForm = formFactory.form(MapaDTO.class).fill(mapaDTO);

            return ok(views.html.mapas.editar.render(
                    id,
                    mapaDTOForm,
                    request
            ));

        }, classLoaderExecutionContext.current());
    }

    /**
     * Editar um mapa existente apartir do id
     *
     * @param request requisicao
     * @param id do mapa a ser editado
     */
    public CompletionStage<Result> editar(Http.Request request, Long id) {

        Form<MapaDTO> mapaDTOForm = formFactory.form(MapaDTO.class).bindFromRequest(request);

        if (mapaDTOForm.hasErrors()) {
            return CompletableFuture.completedFuture(
                    badRequest(views.html.mapas.editar.render(
                            id,
                            mapaDTOForm,
                            request
                    ))
            );
        }

        MapaDTO mapaDto = mapaDTOForm.get();

        return mapaRepository.obterMapaPorNome(mapaDto.getNome()).thenComposeAsync(optionalMapa -> {

            if (optionalMapa.isPresent()) {

                Form<MapaDTO> formComErro = mapaDTOForm.withGlobalError("Já existe um mapa cadastrado com o nome '" + optionalMapa.get().getNome() + "'.");

                return CompletableFuture.completedFuture(
                    badRequest(views.html.mapas.editar.render(
                        id,
                        formComErro,
                        request
                    ))
                );

            }

            Mapa mapa;

            try {

                Calendar dataHoraAlteracao = Calendar.getInstance();

                mapa = Mapa.converterDTOMapa(mapaDto);
                mapa.setDataAlteracao(dataHoraAlteracao);

            } catch (ParseException e) {
                Form<MapaDTO> formComErro = mapaDTOForm.withGlobalError("Erro ao converter os dados do mapa '" + mapaDto.getNome() + "'.");
                return CompletableFuture.completedFuture(
                        badRequest(views.html.mapas.editar.render(
                                id,
                                formComErro,
                                request
                        ))
                );
            }

            // Faz update do mapa no banco e redireciona
            return mapaRepository.update(id, mapa).thenApplyAsync(data ->
                            redirect(routes.MapaController.listar(0, "nome", "asc", ""))
                                    .flashing("success", "Mapa '" + mapa.getNome() + "' foi alterado com sucesso!"),
                    classLoaderExecutionContext.current()
            );

        }, classLoaderExecutionContext.current());

    }

    /**
     * Remove um mapa existente apartir do id
     * TODO
     * @param request requisicao
     * @param id do mapa a ser editado
     */
    public CompletionStage<Result> remover(Http.Request request, Long id) {
        return null;
    }

    public CompletionStage<Result> inserir(Http.Request request) {

        Form<MapaDTO> mapaDTOForm = formFactory.form(MapaDTO.class).bindFromRequest(request);

        if (mapaDTOForm.hasErrors()) {
            return CompletableFuture.completedFuture(
                badRequest(views.html.mapas.cadastrar.render(
                    mapaDTOForm,
                    request
                ))
            );
        }

        MapaDTO mapaDto = mapaDTOForm.get();

        return mapaRepository.obterMapaPorNome(mapaDto.getNome()).thenComposeAsync(optionalMapa -> {

            if (optionalMapa.isPresent()) {
                // Já existe um mapa com esse nome, retorna com erro
                Form<MapaDTO> formComErro = mapaDTOForm.withGlobalError("Já existe um mapa cadastrado com o nome '" + optionalMapa.get().getNome() + "'.");
                return CompletableFuture.completedFuture(
                    badRequest(views.html.mapas.cadastrar.render(
                        formComErro,
                        request
                    ))
                );
            }

            Mapa mapa;

            try {

                Calendar dataHoraCadastro = Calendar.getInstance();

                mapa = Mapa.converterDTOMapa(mapaDto);
                mapa.setDataCadastro(dataHoraCadastro);
                mapa.setDataAlteracao(dataHoraCadastro);

            } catch (ParseException e) {
                Form<MapaDTO> formComErro = mapaDTOForm.withGlobalError("Erro ao converter os dados do mapa '" + mapaDto.getNome() + "'.");
                return CompletableFuture.completedFuture(
                        badRequest(views.html.mapas.cadastrar.render(
                                formComErro,
                                request
                        ))
                );
            }

            // Insere o mapa no banco
            return mapaRepository.insert(mapa).thenApplyAsync(data ->
                            redirect(routes.MapaController.listar(0, "nome", "asc", ""))
                                    .flashing("success", "Mapa '" + mapa.getNome() + "' foi criado com sucesso!"),
                    classLoaderExecutionContext.current()
            );

        }, classLoaderExecutionContext.current());

    }
    
}
