package mzajac.scalajstut.webapp.contacts.model

/**
  * Created by michal on 15.04.16.
  */
case class Contact(id: Long, name: String, email: Option[String], description: Option[String])
