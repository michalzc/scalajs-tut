package mzajac.scalajstut.webapp.contacts.components

import japgolly.scalajs.react.{BackendScope, ReactElement}
import mzajac.scalajstut.webapp.contacts.model.Contact
import japgolly.scalajs.react.vdom.prefix_<^._

/**
  * Created by michal on 15.04.16.
  */
trait ContactListProps {
  def loadContacts: Seq[Contact]
}

class ContactListBackend($: BackendScope[ContactListProps, Seq[Contact]]) {

  def loadContactList = $.props.flatMap(props => $.modState(_ => props.loadContacts))

  def render(state: Seq[Contact]): ReactElement = <.div(
    <.h1("Contacts"),
    <.ul(
      state
        .filter(c => c.email.isDefined)
        .map { contact => ContactComponent(contact) }
    ),
    ContactForm()
  )
}
