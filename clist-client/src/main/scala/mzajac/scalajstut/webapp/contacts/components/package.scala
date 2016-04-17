package mzajac.scalajstut.webapp.contacts

import japgolly.scalajs.react.ReactComponentB
import mzajac.scalajstut.webapp.contacts.model.{Contact, ContactEntry}

/**
  * Created by michal on 15.04.16.
  */
package object components {

  val ContactComponent = ReactComponentB[Contact]("Contact")
    .renderBackend[ContactBackend]
    .build

  val ContactListComponent = ReactComponentB[ContactListProps]("ContactList")
    .initialState[Seq[Contact]](Nil)
    .renderBackend[ContactListBackend]
    .componentDidMount(_.backend.loadContactList)
    .build

  val ContactForm = ReactComponentB[ContactEntry]("ContactForm")
    .initialState(ContactEntry())
    .renderBackend[ContactFormBackend]
    .build
}
