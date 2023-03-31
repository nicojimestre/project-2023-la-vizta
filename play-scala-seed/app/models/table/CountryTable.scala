package models.table

import models.Country
import models.Types.{CountryId, Geometry, Region}
import slick.jdbc.PostgresProfile.api._

class CountryTable(tag: Tag) extends Table[Country](_tableTag = tag, _tableName = "country") {

	def id = column[CountryId]("id", O.PrimaryKey)
	def name = column[Region]("name")
	def geom = column[Geometry]("geom")

	def * = (id, name, geom) <> (Country.tupled, Country.unapply)
}
