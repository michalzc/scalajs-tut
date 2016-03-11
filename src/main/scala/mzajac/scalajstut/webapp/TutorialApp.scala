package mzajac.scalajstut.webapp

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom
import org.scalajs.jquery.jQuery
import dom.document


object TutorialApp extends JSApp {
  def main(): Unit = {
    appendPar(document.body, "Hello World!")
  }

  def appendPar(targetNode: dom.Node, text: String): Unit = {
    jQuery("body").append(s"<p>$text</p>")
  }

  @JSExport
  def addClickMessage(): Unit = {
    appendPar(document.body, "Button clicked!")
  }
}
