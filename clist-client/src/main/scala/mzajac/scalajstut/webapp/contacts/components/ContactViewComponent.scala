package mzajac.scalajstut.webapp.contacts.components

//import japgolly.scalajs.react.{BackendScope, ReactComponentB, ReactElement, NoArgs}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import mzajac.scalajstut.webapp.contacts.model.Contact

/**
  * Created by michal on 15.04.16.
  */
object ContactViewComponent {

  class ContactBackend($: BackendScope[Contact, Unit]) {

    def render(contact: Contact): ReactElement =
      <.li(
        <.h2(contact.name),
        contact.email.map(e => <.a(^.href := "mailto:" + e, e)).getOrElse(EmptyTag),
        <.div(contact.description.getOrElse(""): String))

  }

  val ContactView = ReactComponentB[Contact]("Contact")
    .renderBackend[ContactBackend]
    .build

}