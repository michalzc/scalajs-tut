package mzajac.scalajstut.webapp.contacts

import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{ReactComponentB, ReactDOM}
import mzajac.scalajstut.webapp.contacts.model.Contact
import org.scalajs.dom.document

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport
import scalaz.syntax.std.option._

object ContactsApp extends JSApp {

//  type State = Seq[Contact]
//
//
//  case class MainComponentProps(contacts: Seq[Contact])
//
//  val ContactComponent = ReactComponentB[Contact]("ContactComponent")
//    .render_P { contact =>
//      <.li(
//        <.h2(contact.name),
//        contact.email.map(e => <.a(^.href := "mailto:" + e, e)).getOrElse(EmptyTag),
//        <.div(contact.description.getOrElse(""): String)
//      )
//    }.build
//
//  val iState: State = Nil
//
//  val MainComponent = ReactComponentB[MainComponentProps]("ContactListComponent")
//    .initialState(iState)
//    .render_P { state =>
//      <.div(
//        <.h1("Contacts"),
//        <.ul(
//          state
//            .contacts
//            .filter(c => c.email.isDefined)
//            .map { contact => ContactComponent(contact) }
//        ))
//    }.build

  @JSExport
  def main(): Unit = {
    val initialState = List(
      Contact(1, "Jozin z Bazin", "jozin@jozin.cz".some, "you know him".some),
      Contact(2, "Joe Guest", "jg@example.com".some, None),
      Contact(3, "Jim", None, None)
    )

    val target = document.getElementById("main-content")
    ReactDOM.render( components.ContactListComponent(), target)
  }
}
