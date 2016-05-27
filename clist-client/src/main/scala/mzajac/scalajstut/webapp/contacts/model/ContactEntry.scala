package mzajac.scalajstut.webapp.contacts.model

/**
  * Created by michal on 17.04.16.
  */
case class ContactEntry(id: Option[Long] = None, name: Option[String] = None, email: Option[String] = None, description: Option[String] = None) {
  def isUpdatable: Boolean = {
    (for {
      id <- id
      name <- name
      email <- email
    } yield (!name.isEmpty && !email.isEmpty)).getOrElse(false)
  }
}