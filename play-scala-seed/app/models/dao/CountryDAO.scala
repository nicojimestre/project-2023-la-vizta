package models.dao

import models.Types.{Geometry, Region}

import scala.concurrent.Future

trait CountryDAO {
	def getCountriesGeometry: Future[Seq[(Region, Geometry)]]
}