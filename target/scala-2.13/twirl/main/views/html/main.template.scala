
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

object main extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template2[String,Html,play.twirl.api.HtmlFormat.Appendable] {

  /*
 * This template is called from the `index` template. This template
 * handles the rendering of the page header and body tags. It takes
 * two arguments, a `String` for the title of the page and an `Html`
 * object to insert into the body of the page.
 */
  def apply/*7.2*/(title: String)(content: Html):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*8.1*/("""
"""),format.raw/*9.1*/("""<!DOCTYPE html>
<html lang="en">

<head>
    <title>"""),_display_(/*13.13*/title),format.raw/*13.18*/("""</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" type="image/png" href='"""),_display_(/*15.55*/routes/*15.61*/.Assets.versioned("images/favicon.png")),format.raw/*15.100*/("""'>
    <link rel="stylesheet" href=""""),_display_(/*16.35*/routes/*16.41*/.Assets.versioned("lib/bootstrap/css/bootstrap.min.css")),format.raw/*16.97*/("""">
</head>

<body>
    <main>
        <div class="container">
            """),_display_(/*22.14*/navbar()),format.raw/*22.22*/("""

            """),format.raw/*24.13*/("""<div class="mb-4">
                """),_display_(/*25.18*/content),format.raw/*25.25*/("""
            """),format.raw/*26.13*/("""</div>

            """),_display_(/*28.14*/footer()),format.raw/*28.22*/("""
        """),format.raw/*29.9*/("""</div>
    </main>
    <script src=""""),_display_(/*31.19*/routes/*31.25*/.Assets.versioned("lib/jquery/jquery.min.js")),format.raw/*31.70*/(""""></script>
    <script src=""""),_display_(/*32.19*/routes/*32.25*/.Assets.versioned("lib/bootstrap/js/bootstrap.bundle.min.js")),format.raw/*32.86*/(""""></script>
</body>

</html>"""))
      }
    }
  }

  def render(title:String,content:Html): play.twirl.api.HtmlFormat.Appendable = apply(title)(content)

  def f:((String) => (Html) => play.twirl.api.HtmlFormat.Appendable) = (title) => (content) => apply(title)(content)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/main.scala.html
                  HASH: 35b98e795aa887e791d7f1116f47f2b1aa4cd7a4
                  MATRIX: 1165->260|1289->291|1316->292|1396->345|1422->350|1587->488|1602->494|1663->533|1727->570|1742->576|1819->632|1921->707|1950->715|1992->729|2055->765|2083->772|2124->785|2172->806|2201->814|2237->823|2301->860|2316->866|2382->911|2439->941|2454->947|2536->1008
                  LINES: 32->7|37->8|38->9|42->13|42->13|44->15|44->15|44->15|45->16|45->16|45->16|51->22|51->22|53->24|54->25|54->25|55->26|57->28|57->28|58->29|60->31|60->31|60->31|61->32|61->32|61->32
                  -- GENERATED --
              */
          