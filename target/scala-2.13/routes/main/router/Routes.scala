// @GENERATOR:play-routes-compiler
// @SOURCE:conf/routes

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:7
  HomeController_0: controllers.HomeController,
  // @LINE:16
  Assets_1: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

  @javax.inject.Inject()
  def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:7
    HomeController_0: controllers.HomeController,
    // @LINE:16
    Assets_1: controllers.Assets
  ) = this(errorHandler, HomeController_0, Assets_1, "/")

  def withPrefix(addPrefix: String): Routes = {
    val prefix = play.api.routing.Router.concatPrefix(addPrefix, this.prefix)
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, HomeController_0, Assets_1, prefix)
  }

  private val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.HomeController.index"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """jogadores/cadastrar""", """controllers.HomeController.telaCadastroJogador"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """jogadores/listar""", """controllers.HomeController.telaListaJogador"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """estatisticas/cadastrar""", """controllers.HomeController.telaEstatisticasCadastrar"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """estatisticas/inicio""", """controllers.HomeController.telaEstatisticasInicio"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.versioned(path:String = "/public", file:Asset)"""),
    Nil
  ).foldLeft(Seq.empty[(String, String, String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String, String, String)]
    case l => s ++ l.asInstanceOf[List[(String, String, String)]]
  }}


  // @LINE:7
  private lazy val controllers_HomeController_index0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private lazy val controllers_HomeController_index0_invoker = createInvoker(
    HomeController_0.index,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "index",
      Nil,
      "GET",
      this.prefix + """""",
      """""",
      Seq()
    )
  )

  // @LINE:9
  private lazy val controllers_HomeController_telaCadastroJogador1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("jogadores/cadastrar")))
  )
  private lazy val controllers_HomeController_telaCadastroJogador1_invoker = createInvoker(
    HomeController_0.telaCadastroJogador,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "telaCadastroJogador",
      Nil,
      "GET",
      this.prefix + """jogadores/cadastrar""",
      """""",
      Seq()
    )
  )

  // @LINE:10
  private lazy val controllers_HomeController_telaListaJogador2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("jogadores/listar")))
  )
  private lazy val controllers_HomeController_telaListaJogador2_invoker = createInvoker(
    HomeController_0.telaListaJogador,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "telaListaJogador",
      Nil,
      "GET",
      this.prefix + """jogadores/listar""",
      """""",
      Seq()
    )
  )

  // @LINE:12
  private lazy val controllers_HomeController_telaEstatisticasCadastrar3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("estatisticas/cadastrar")))
  )
  private lazy val controllers_HomeController_telaEstatisticasCadastrar3_invoker = createInvoker(
    HomeController_0.telaEstatisticasCadastrar,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "telaEstatisticasCadastrar",
      Nil,
      "GET",
      this.prefix + """estatisticas/cadastrar""",
      """""",
      Seq()
    )
  )

  // @LINE:13
  private lazy val controllers_HomeController_telaEstatisticasInicio4_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("estatisticas/inicio")))
  )
  private lazy val controllers_HomeController_telaEstatisticasInicio4_invoker = createInvoker(
    HomeController_0.telaEstatisticasInicio,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "telaEstatisticasInicio",
      Nil,
      "GET",
      this.prefix + """estatisticas/inicio""",
      """""",
      Seq()
    )
  )

  // @LINE:16
  private lazy val controllers_Assets_versioned5_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""", encodeable=false)))
  )
  private lazy val controllers_Assets_versioned5_invoker = createInvoker(
    Assets_1.versioned(fakeValue[String], fakeValue[Asset]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "versioned",
      Seq(classOf[String], classOf[Asset]),
      "GET",
      this.prefix + """assets/""" + "$" + """file<.+>""",
      """ Map static resources from the /public folder to the /assets URL path""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:7
    case controllers_HomeController_index0_route(params@_) =>
      call { 
        controllers_HomeController_index0_invoker.call(HomeController_0.index)
      }
  
    // @LINE:9
    case controllers_HomeController_telaCadastroJogador1_route(params@_) =>
      call { 
        controllers_HomeController_telaCadastroJogador1_invoker.call(HomeController_0.telaCadastroJogador)
      }
  
    // @LINE:10
    case controllers_HomeController_telaListaJogador2_route(params@_) =>
      call { 
        controllers_HomeController_telaListaJogador2_invoker.call(HomeController_0.telaListaJogador)
      }
  
    // @LINE:12
    case controllers_HomeController_telaEstatisticasCadastrar3_route(params@_) =>
      call { 
        controllers_HomeController_telaEstatisticasCadastrar3_invoker.call(HomeController_0.telaEstatisticasCadastrar)
      }
  
    // @LINE:13
    case controllers_HomeController_telaEstatisticasInicio4_route(params@_) =>
      call { 
        controllers_HomeController_telaEstatisticasInicio4_invoker.call(HomeController_0.telaEstatisticasInicio)
      }
  
    // @LINE:16
    case controllers_Assets_versioned5_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned5_invoker.call(Assets_1.versioned(path, file))
      }
  }
}
