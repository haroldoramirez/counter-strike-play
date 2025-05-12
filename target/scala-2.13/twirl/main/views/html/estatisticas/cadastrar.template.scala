
package views.html.estatisticas

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

"""),_display_(/*3.2*/main("Counter Strike Stats | Cadastrar Estatísticas")/*3.55*/ {_display_(Seq[Any](format.raw/*3.57*/("""

"""),format.raw/*5.1*/("""<div class="card shadow rounded">
    <div class="card-header">
        <h4 class="mb-0">Registro do status do jogador</h4>
    </div>
    <div class="card-body">
        <form id="formularioRegistro" method="post">
            <div class="mb-3">
                <label for="name" class="form-label">Nome</label>
                <input type="text" class="form-control" id="name" name="name" placeholder="Digite o nome do jogador" required />
            </div>

            <div class="mb-3">
                <label for="kills" class="form-label">Kills</label>
                <input type="number" class="form-control" id="kills" name="kills" placeholder="Quantidade de eliminações" min="0" required />
            </div>

            <div class="mb-3">
                <label for="deaths" class="form-label">Deaths</label>
                <input type="number" class="form-control" id="deaths" name="deaths" placeholder="Quantidade de mortes" min="0" required />
            </div>

            <div class="mb-3">
                <label for="damage" class="form-label">Damage</label>
                <input type="number" class="form-control" id="damage" name="damage" placeholder="Dano causado" min="0" required />
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
                  SOURCE: app/views/estatisticas/cadastrar.scala.html
                  HASH: 9e440ecd5373175907030e2faef69c6202cd2320
                  MATRIX: 917->1|1013->3|1043->8|1104->61|1143->63|1173->67
                  LINES: 27->1|32->1|34->3|34->3|34->3|36->5
                  -- GENERATED --
              */
          