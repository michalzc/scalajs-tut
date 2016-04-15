package mzajac.scalajstut.webapp.contacts

import japgolly.scalajs.react.ReactComponentB
import mzajac.scalajstut.webapp.contacts.model.Contact

/**
  * Created by michal on 15.04.16.
  */
package object components {

  type ContactListState = Seq[Contact]

  val ContactComponent = ReactComponentB[Contact]("Contact")
    .renderBackend[ContactBackend]
    .build

  val ContactListComponent = ReactComponentB[Unit]("ContactList")
    .initialState[ContactListState](Nil)
    .renderBackend[ContactListBackend]
    .build

}
