package mzajac.scalajstut.webapp

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom
import org.scalajs.jquery.jQuery
import dom.document


object TutorialApp extends JSApp {
  def main(): Unit = {
    jQuery(setupUI _)
  }

  // @JSExport
  def addClickMessage(): Unit = {
    jQuery("body").append("<p>Button Clicked!</p>")
  }

  def setupUI(): Unit = {
    jQuery("body").append(s"<p>Hello World!</p>")
    jQuery("#click-me-button").click(addClickMessage _)
  }
}
