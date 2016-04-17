package mzajac.scalajstut.webapp.contacts.components

import japgolly.scalajs.react.{BackendScope, Callback, ReactElement}
import mzajac.scalajstut.webapp.contacts.model.ContactEntry
import japgolly.scalajs.react.vdom.prefix_<^._
import slogging.LazyLogging
import japgolly.scalajs.react.{ReactEventI, ReactEventTA}
import scalaz.syntax.std.option._


/**
  * Created by michal on 17.04.16.
  */
class ContactFormBackend($: BackendScope[ContactEntry, ContactEntry]) extends LazyLogging {

  def updateContact(ev: ReactEventI) = {

    ev.preventDefaultCB >>
    $.state.flatMap { contactEntry =>
      $.props.map { initialEntry =>
        contactEntry.copy(id = initialEntry.id)
      }
    }.tap{ contact =>
      logger.info(s"Contact to send: $contact")

    }
  }

  def updateName(ev: ReactEventI) = {
    val newName = ev.target.value
    $.modState(_.copy(name = newName.some))
  }

  def updateEmail(ev: ReactEventI) = {
    val newEmail = ev.target.value
    $.modState(_.copy(email = newEmail.some))
  }

  def updateDescription(ev: ReactEventTA) = {
    val newDescription = ev.target.value
    $.modState(_.copy(description = newDescription.some))
  }


  def render(props: ContactEntry): ReactElement =
    <.form(^.className := "Contact-form", ^.onSubmit ==> updateContact,
      <.fieldset(
        <.legend("New contact"),
        <.input(^.`type` := "text", ^.placeholder := "name", ^.value := props.name, ^.onChange ==> updateName), <.br,
        <.input(^.`type` := "email", ^.placeholder := "email", ^.value := props.email, ^.onChange ==> updateEmail), <.br,
        <.textarea(^.placeholder := "description", ^.value := props.description, ^.onChange ==> updateDescription), <.br,
        <.input(^.`type` := "submit", ^.value := "Add contact")
      )
    )
}
