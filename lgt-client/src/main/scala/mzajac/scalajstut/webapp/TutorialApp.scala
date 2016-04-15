package mzajac.scalajstut.webapp

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom.document
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{ReactComponentB, ReactDOM}

import scalaz.syntax.std.option._

object TutorialApp extends JSApp {

  case class Contact(name: String, email: Option[String], description: Option[String])

  case class MainComponentState(contacts: Seq[Contact])

  val ContactComponent = ReactComponentB[Contact]("ContactComponent")
      .render_P{contact =>
        <.li(
          <.h2(contact.name),
          contact.email.map(e => <.a(^.href := "mailto:" + e, e)).getOrElse(<.span()),
          <.div(contact.description.getOrElse(""): String)
        )
      }.build

  val MainComponent = ReactComponentB[MainComponentState]("ContactListComponent")
    .render_P { state =>
      <.div(
        <.h1("Contacts"),
        <.ul(
          state
            .contacts
            .filter(c => c.email.isDefined)
            .map { contact => ContactComponent(contact)}
        ))
    }.build

  @JSExport
  def main(): Unit = {
    val target = document.getElementById("main-content")
    println(s"Target: $target")
    ReactDOM.render(MainComponent(MainComponentState(List(
      Contact("Jozin z Bazin", "jozin@jozin.cz".some, "you know him".some),
      Contact("Joe Guest", "jg@example.com".some, None),
      Contact("Jim", None, None)
    ))), target)
  }
}
