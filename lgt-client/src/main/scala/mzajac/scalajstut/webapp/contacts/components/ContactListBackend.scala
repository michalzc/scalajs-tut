package mzajac.scalajstut.webapp.contacts.components

import japgolly.scalajs.react.{BackendScope, ReactElement}
import mzajac.scalajstut.webapp.contacts.model.Contact
import japgolly.scalajs.react.vdom.prefix_<^._

/**
  * Created by michal on 15.04.16.
  */
class ContactListBackend($: BackendScope[Unit, ContactListState]) {
  type State = Seq[Contact]

  def render(state: State): ReactElement = <.div(
    <.h1("Contacts"),
    <.ul(
      state
        .filter(c => c.email.isDefined)
        .map { contact => ContactComponent(contact) }
    ))
}
