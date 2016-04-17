package mzajac.scalajstut.webapp.contacts

import japgolly.scalajs.react.ReactDOM
import mzajac.scalajstut.webapp.contacts.components.ContactListProps
import mzajac.scalajstut.webapp.contacts.model.Contact
import org.scalajs.dom.document
import slogging._

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport
import scalaz.syntax.std.option._

object ContactsApp extends JSApp with LazyLogging {

  LoggerConfig.factory = PrintLoggerFactory
  LoggerConfig.level = LogLevel.DEBUG

  @JSExport
  def main(): Unit = {
    val contacts = List(
      Contact(1, "Jozin z Bazin", "jozin@jozin.cz".some, "you know him".some),
      Contact(2, "Joe Guest", "jg@example.com".some, None),
      Contact(3, "Jim", None, None)
    )

    val target = document.getElementById("main-content")
    val contactList = components.ContactListComponent(new ContactListProps {
      override def loadContacts: Seq[Contact] = {
        logger.info("Getting initial state from props")
        contacts
      }
    })
    ReactDOM.render( contactList, target)
  }
}
