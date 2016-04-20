package mzajac.scalajstut.webapp.contacts.components

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement, Ref}
import mzajac.scalajstut.webapp.contacts.model.{Contact, ContactEntry}
import japgolly.scalajs.react.vdom.prefix_<^._
import mzajac.scalajstut.webapp.contacts.components.ContactFormComponent.{ContactForm, ContactFormProps}
import mzajac.scalajstut.webapp.contacts.components.ContactViewComponent.{ContactView, ContactViewProps}
import slogging.LazyLogging

import scalaz.syntax.std.option._

/**
  * Created by michal on 15.04.16.
  */
object ContactListComponent {

  case class ContactListState(formContact: ContactEntry = ContactEntry(), contacts: Map[Long, Contact] = Map.empty)

  trait ContactListProps {
    def loadContacts: List[Contact]
  }

  class ContactListBackend(scope: BackendScope[ContactListProps, ContactListState]) extends LazyLogging {

    def loadContactList = scope.props.flatMap {
      props => scope.modState { state =>
        val contacts = props.loadContacts.map(c => (c.id -> c)).toMap

        state.copy(contacts = contacts)
      }
    }

    def setContactToForm(contact: Contact): Callback = {
      logger.debug(s"Contact to update form: $contact")
      scope.modState(_.copy(formContact = ContactEntry(contact.id.some, contact.name.some, contact.email, contact.description)))
    }

    def updateContact(contact: ContactEntry): Callback = {
      logger.info(s"Received contact: $contact")
      scope.modState { state =>
        val newContact = Contact(contact.id.getOrElse(state.contacts.keys.max + 1), contact.name.get, contact.email, contact.description)
        val newState = ContactListState(ContactEntry(), state.contacts + (newContact.id -> newContact))
        logger.debug(s"Updating state: $newState")
        newState
      }
    }

    def render(state: ContactListState): ReactElement = {
      logger.info("Rendering contact list")
      <.div(
        <.h1("Contacts"),
        <.ul(
          state
            .contacts
            .values
            .filter(c => c.email.isDefined)
            .map { contact => ContactView.withKey(contact.id)(ContactViewProps(contact, setContactToForm)) }
        ),
        ContactForm(ContactFormProps(state.formContact, updateContact))
      )
    }

  }


  val ContactList = ReactComponentB[ContactListProps]("ContactList")
    .initialState[ContactListState](ContactListState())
    .renderBackend[ContactListBackend]
    .componentDidMount(_.backend.loadContactList)
    .build

}
