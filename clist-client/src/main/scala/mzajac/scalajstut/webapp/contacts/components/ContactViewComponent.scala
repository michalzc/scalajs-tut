package mzajac.scalajstut.webapp.contacts.components

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement}
import japgolly.scalajs.react.vdom.prefix_<^._
import mzajac.scalajstut.webapp.contacts.model.{Contact, ContactEntry}
import slogging.LazyLogging
import ContactFormComponent.{ContactForm, ContactFormProps}

/**
  * Created by michal on 15.04.16.
  */
object ContactViewComponent {

  case class ContactViewProps(contact: Contact, updateContactHandler: ContactEntry => Callback)

  case class ContactViewState(edit: Boolean = false)

  class ContactBackend(scope: BackendScope[ContactViewProps, ContactViewState]) extends LazyLogging {

    def updateContact(contactEntry: ContactEntry): Callback = {
//      scope.props.flatMap { props =>
//        props.updateContactHandler(contactEntry)
//      } >> scope.modState(_.copy(edit = false))
      for {
        props <- scope.props
        _ <- props.updateContactHandler(contactEntry)
        _ <- scope.modState(_.copy(edit = false))
      } yield (())
    }

    def setEditContact: Callback = {
      scope.modState(_.copy(edit = true))
    }

    def render(props: ContactViewProps, state: ContactViewState): ReactElement = {
      logger.info(s"State: $state")

      if (state.edit) {
        import props.contact._
        import scalaz.syntax.std.option._

        ContactForm(ContactFormProps(ContactEntry(id.some, name.some, email, description), updateContact))
      } else {

        <.li(
          <.h2(props.contact.name, <.button(^.`type` := "button", ^.onClick --> setEditContact, ^.className := "btn glyphicon glyphicon-edit")),
          props.contact.email.map(e => <.a(^.href := "mailto:" + e, e)).getOrElse(EmptyTag),
          <.div(props.contact.description.getOrElse(""): String))
      }
    }

  }

  val ContactView = ReactComponentB[ContactViewProps]("Contact")
    .initialState(ContactViewState())
    .renderBackend[ContactBackend]
    .build

}