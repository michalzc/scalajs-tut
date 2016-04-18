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

      val newContactCb = for {
        p <- scope.props
        contact <- scope.state
        newContact = contact.copy(id = p.contact.id)
        updateFun = p.updateContact
      } yield (updateFun, newContact)

      val updateCb: Callback = newContactCb.flatMap { case (updateFun, newContact) =>
        if(newContact.name.isDefined && newContact.email.isDefined) {
          logger.debug(s"Updating contact: $newContact")
          updateFun(newContact)
        }
        else {
          logger.debug("Invalid component - doing nothing")
          Callback.empty
        }
      }


      ev.preventDefaultCB >> updateCb


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

    def render(props: ContactFormProps): ReactElement =
      <.form(^.className := "Contact-form", ^.onSubmit ==> updateContact,
        <.fieldset(
          <.legend("New contact"),
          <.input(^.`type` := "text", ^.placeholder := "name", ^.value := props.contact.name, ^.onChange ==> updateName), <.br,
          <.input(^.`type` := "email", ^.placeholder := "email", ^.value := props.contact.email, ^.onChange ==> updateEmail), <.br,
          <.textarea(^.placeholder := "description", ^.value := props.contact.description, ^.onChange ==> updateDescription), <.br,
          <.input(^.`type` := "submit", ^.value := "Add contact")
        )
      )
  }

  val ContactForm = ReactComponentB[ContactFormProps]("ContactForm")
    .initialState(ContactEntry())
    .renderBackend[ContactFormBackend]
    .build
}