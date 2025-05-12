
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

object index extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/():play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("Counter Strike Stats | Index")/*3.38*/ {_display_(Seq[Any](format.raw/*3.40*/("""
"""),format.raw/*4.1*/("""<div class="p-5 bg-light rounded-3">
  <div class="container-fluid py-5">
    <h1 class="display-5 fw-bold">Seja bem-vindo!</h1>
    <p class="col-md-10 fs-4">Esta aplicação tem como objetivo registrar e gerar estatísticas do desempenho de jogadores da comunidade de Counter Strike 2 após o final de cada partida.</p>
    <a href=""""),_display_(/*8.15*/routes/*8.21*/.HomeController.telaEstatisticasCadastrar),format.raw/*8.62*/("""" type="button" class="btn btn-success btn-lg px-4">Inserir registros</a>
    <a href=""""),_display_(/*9.15*/routes/*9.21*/.HomeController.telaEstatisticasInicio),format.raw/*9.59*/("""" class="btn btn-primary btn-lg" type="button">Acessar estatísticas</a>
  </div>
</div>
""")))}))
      }
    }
  }

  def render(): play.twirl.api.HtmlFormat.Appendable = apply()

  def f:(() => play.twirl.api.HtmlFormat.Appendable) = () => apply()

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/index.scala.html
                  HASH: 4167534237731db5aff485a5ba81476faabddec6
                  MATRIX: 900->1|996->4|1023->6|1067->42|1106->44|1133->45|1491->377|1505->383|1566->424|1680->512|1694->518|1752->556
                  LINES: 27->1|32->2|33->3|33->3|33->3|34->4|38->8|38->8|38->8|39->9|39->9|39->9
                  -- GENERATED --
              */
          