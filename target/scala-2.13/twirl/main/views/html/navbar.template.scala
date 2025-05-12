
package views.html

import _root_.play.twirl.api.TwirlFeatureImports._
import _root_.play.twirl.api.TwirlHelperImports._
import _root_.play.twirl.api.Html
import _root_.play.twirl.api.JavaScript
import _root_.play.twirl.api.Txt
import _root_.play.twirl.api.Xml
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import java.lang._
import java.util._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.api.data.Field
import play.data._
import play.core.j.PlayFormsMagicForJava._
import scala.jdk.CollectionConverters._

object navbar extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply():play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.1*/("""<nav class="navbar navbar-expand-lg navbar-light bg-light rounded mb-4" aria-label="Eleventh navbar example">
  <div class="container-fluid">

    <a class="navbar-brand" href=""""),_display_(/*4.36*/routes/*4.42*/.HomeController.index),format.raw/*4.63*/("""">Counter Strike Stats</a>

    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsExample09" aria-controls="navbarsExample09" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarsExample09">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href=""""),_display_(/*13.65*/routes/*13.71*/.HomeController.telaEstatisticasInicio),format.raw/*13.109*/("""">Estatísticas</a>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" id="dropdown09" data-bs-toggle="dropdown" aria-expanded="false">Jogadores</a>
          <ul class="dropdown-menu" aria-labelledby="dropdown09">
            <li><a class="dropdown-item" href=""""),_display_(/*18.49*/routes/*18.55*/.HomeController.telaListaJogador),format.raw/*18.87*/("""">Listar</a></li>
            <li><a class="dropdown-item" href=""""),_display_(/*19.49*/routes/*19.55*/.HomeController.telaCadastroJogador),format.raw/*19.90*/("""">Cadastrar</a></li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</nav>"""))
      }
    }
  }

  def render(): play.twirl.api.HtmlFormat.Appendable = apply()

  def f:(() => play.twirl.api.HtmlFormat.Appendable) = () => apply()

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/navbar.scala.html
                  HASH: cac2a54ee3db445be635960b3b91989e1f7b9c43
                  MATRIX: 990->0|1197->181|1211->187|1252->208|1786->715|1801->721|1861->759|2212->1083|2227->1089|2280->1121|2374->1188|2389->1194|2445->1229
                  LINES: 32->1|35->4|35->4|35->4|44->13|44->13|44->13|49->18|49->18|49->18|50->19|50->19|50->19
                  -- GENERATED --
              */
          