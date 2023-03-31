package models.dao

import models.Country
import models.Types._
import models.table.CountryTable
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.PostgresProfile.api._
import slick.jdbc.{GetResult, JdbcProfile}
import slick.lifted.TableQuery

import javax.inject.Inject
import scala.concurrent.Future

class CountryDAOImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends CountryDAO {

    private lazy val countries = TableQuery[CountryTable]
    private val db = dbConfigProvider.get[JdbcProfile].db

    implicit val getCountriesResult: GetResult[Country] = GetResult(r =>
        Country(r.<<, r.<<, r.<<)
    )

    def getCountriesGeometry: Future[Seq[(Region, Geometry)]] =
        db run {
            sql"""
				select "name", ST_AsGeoJSON("geom")
	            from country
            """.as[(Region, Geometry)]
        }
}
