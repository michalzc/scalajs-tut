package mzajac.scalajstut.webapp.contacts.components

import japgolly.scalajs.react.{BackendScope, Callback, CallbackTo, ReactComponentB, ReactElement, ReactEventI, ReactEventTA}
import mzajac.scalajstut.webapp.contacts.model.ContactEntry
import japgolly.scalajs.react.vdom.prefix_<^._
import slogging.LazyLogging

import scalaz.syntax.std.option._


/**
  * Created by michal on 17.04.16.
  */
object ContactFormComponent {

  case class ContactFormProps(contact: ContactEntry, updateContact: ContactEntry => Callback)

  class ContactFormBackend(scope: BackendScope[ContactFormProps, ContactEntry]) extends LazyLogging {

    def updateContact(ev: ReactEventI): Callback = {
      val updateCallback = for {
        contact <- scope.state
        props <- scope.props
        result <- Callback.when(contact.isUpdatable)(props.updateContact(contact))
      } yield (result)

      ev.preventDefaultCB >> updateCallback
    }

    def updateName(ev: ReactEventI) = {
      val newName = ev.target.value
      scope.modState(_.copy(name = newName.some))
    }

    def updateEmail(ev: ReactEventI) = {
      val newEmail = ev.target.value
      scope.modState(_.copy(email = newEmail.some))
    }

    def updateDescription(ev: ReactEventTA) = {
      val newDescription = ev.target.value
      scope.modState(_.copy(description = newDescription.some))
    }

    def render(props: ContactFormProps): ReactElement = {
      logger.debug(s"Rendering contact form with: ${props.contact}")
      <.form(^.className := "Contact-form", ^.onSubmit ==> updateContact, ^.autoComplete := "off",
        <.fieldset(
          <.legend("Edit"),
          <.input(^.`type` := "text", ^.autoComplete := "off", ^.placeholder := "name", ^.defaultValue := props.contact.name, ^.onChange ==> updateName), <.br,
          <.input(^.`type` := "email", ^.autoComplete := "off", ^.placeholder := "email", ^.defaultValue := props.contact.email, ^.onChange ==> updateEmail), <.br,
          <.textarea(^.placeholder := "description", ^.autoComplete := "off", ^.defaultValue := props.contact.description, ^.onChange ==> updateDescription), <.br,
          <.input(^.`type` := "submit", ^.value := "Save")
        )
      )
    }
  }

  val ContactForm = ReactComponentB[ContactFormProps]("ContactForm")
    .initialState(ContactEntry())
    .renderBackend[ContactFormBackend]
    .componentDidMount(cb => cb.modState(_ => cb.props.contact))
    .build
}