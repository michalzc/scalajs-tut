package mzajac.scalajstut.webapp.contacts.components

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement}
import japgolly.scalajs.react.vdom.prefix_<^._
import mzajac.scalajstut.webapp.contacts.model.Contact
import slogging.LazyLogging

/**
  * Created by michal on 15.04.16.
  */
object ContactViewComponent {

  case class ContactViewProps(contact: Contact, updateContactHandler: Contact => Callback)

  class ContactBackend(scope: BackendScope[ContactViewProps, Unit]) extends LazyLogging {

    def editContact: Callback = {
      scope.props.flatMap { props =>
        props.updateContactHandler(props.contact)
      }
    }

    def render(props: ContactViewProps): ReactElement =
      <.li(
        <.h2(props.contact.name, <.button(^.`type` := "button", ^.onClick --> editContact, ^.className := "btn glyphicon glyphicon-edit")),
        props.contact.email.map(e => <.a(^.href := "mailto:" + e, e)).getOrElse(EmptyTag),
        <.div(props.contact.description.getOrElse(""): String))

  }

  val ContactView = ReactComponentB[ContactViewProps]("Contact")
    .renderBackend[ContactBackend]
    .build

}