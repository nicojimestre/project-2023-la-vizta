package models.dao

import models.SpotifyCharts
import models.Types._
import models.table.SpotifyChartsTable
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.PostgresProfile.api._
import slick.jdbc.{GetResult, JdbcProfile}
import slick.lifted.TableQuery

import javax.inject.Inject
import scala.concurrent.Future

class SpotifyChartsDAOImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends SpotifyChartsDAO {

    private lazy val charts = TableQuery[SpotifyChartsTable]

    private val db = dbConfigProvider.get[JdbcProfile].db

    implicit val getSpotifyChartsResult: GetResult[SpotifyCharts] = GetResult(r =>
        SpotifyCharts(r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<)
    )

    def getFamousTitles: Future[Seq[Title]] =
        db run { charts
            .filter(_.streams > 10_000_000)
            .take(10)
            .map(_.title)
            .result
        }

    def getRecentTopViral50ByCountry(country: Region): Future[(Title, Artist, Url, Streams)] =
        db run { charts
            .filter( c =>
                c.chart === "viral50" &&
                c.date === "2023-03-16" &&
                c.rank === 1 &&
                c.region === country
            )
            .map( c => (c.title, c.artist, c.url, c.streams) )
            .result.head
        }

//    implicit val shape: Shape[
//        FlatShapeLevel,
//        (Rep[Region], Query[
//            (Rep[Title], Rep[Artist], Rep[Url], Rep[Streams], Rep[Geometry]),
//            (Title, Artist, Url, Streams, Geometry),
//            Seq]
//        ),
//        (Region, (Title, Artist, Url, Streams, Geometry)),
//        (SpotifyChartsTable, CountryTable)
//    ] = ???

    def test(): Future[Seq[Any]] =
        db run {
            ???
//            charts
//                .filter(t => t.chart === "top200" && t.region =!= "Global")
//                .sortBy(_.streams.reverse)
//                .groupBy(_.region)
//                .flatMap { case (region, group) => region -> group.take(5).result }
//                .result
        }

    def getMostStreamedPerCountry: Future[Vector[(Region, Title, Date, Url, Streams)]] =
        db run {
            sql"""
				select DISTINCT ON ("region") "region", "title", "date", "url", "streams"
				from "spotify_charts"
				where "rank" = 1 and "streams" > 0
				order by region, "streams" desc
				limit 70
			""".as[(Region, Title, Date, Url, Streams)]
        }
}
