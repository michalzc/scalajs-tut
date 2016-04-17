package mzajac.scalajstut.webapp.contacts.components

import japgolly.scalajs.react.{BackendScope, Callback, ReactElement}
import mzajac.scalajstut.webapp.contacts.model.ContactEntry
import japgolly.scalajs.react.vdom.prefix_<^._
import slogging.LazyLogging


/**
  * Created by michal on 17.04.16.
  */
class ContactFormBackend($: BackendScope[Unit, ContactEntry]) extends LazyLogging {

  def addContact = $.state.tap { contact =>
    logger.info(s"Contact: $contact")
  }


  def render(state: ContactEntry): ReactElement =
    <.form(^.className := "Contact-form",
      <.fieldset(
        <.legend("New contact"),
        <.input(^.`type` := "text", ^.placeholder := "name", ^.value := state.name), <.br,
        <.input(^.`type` := "email", ^.placeholder := "email", ^.value := state.email), <.br,
        <.textarea(^.placeholder := "description", ^.value := state.description), <.br,
        <.input(^.`type` := "button", ^.value := "Add contact", ^.onClick --> addContact.void)
      )
    )
}
