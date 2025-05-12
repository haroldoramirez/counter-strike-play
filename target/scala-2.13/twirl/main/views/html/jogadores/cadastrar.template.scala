
package views.html.jogadores

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

object cadastrar extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/():play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.4*/("""

"""),_display_(/*3.2*/main("Counter Strike Stats | Cadastrar Jogador")/*3.50*/ {_display_(Seq[Any](format.raw/*3.52*/("""

"""),format.raw/*5.1*/("""<div class="card shadow rounded">
  <div class="card-header">
    <h4 class="mb-0">Cadastrar Jogador</h4>
  </div>
  <div class="card-body">
    <form id="formularioRegistro" method="post">
      <div class="mb-3">
        <label for="name" class="form-label">Nome</label>
        <input type="text" class="form-control" id="name" name="name" placeholder="Digite o nome do jogador" required />
      </div>

      <p class="text-muted mb-4">* Todos os campos são obrigatórios</p>

      <button id="registrar" name="registrar" type="button" class="btn btn-success">Registrar</button>
      <button type="button" class="btn btn-default" id="cancelar" name="listar">Cancelar</button>

    </form>
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
                  SOURCE: app/views/jogadores/cadastrar.scala.html
                  HASH: 8cf7b40d586b7e2357350ef421bfb1fca0dd5daf
                  MATRIX: 914->1|1010->3|1040->8|1096->56|1135->58|1165->62
                  LINES: 27->1|32->1|34->3|34->3|34->3|36->5
                  -- GENERATED --
              */
          