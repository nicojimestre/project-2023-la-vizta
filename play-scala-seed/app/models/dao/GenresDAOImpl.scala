package models.dao

import models.Genres
import models.Types.{GenreId, Title, Artist, Region, Streams, Rank, Genre}
import models.table.GenresTable
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.{GetResult, JdbcProfile}
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import javax.inject.Inject
import scala.concurrent.Future

class GenresDAOImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends GenresDAO {

	private lazy val genres = TableQuery[GenresTable]
	private val db = dbConfigProvider.get[JdbcProfile].db

//	implicit val getGenresResult: GetResult[Genres] = GetResult(r =>
//		Genres(r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<)
//	)

	def getGenres: Future[Seq[(GenreId, Title, Artist, Region, Streams, Rank, Genre)]] =
		db run {
			genres.map( g => (g.id, g.title, g.artist, g.region, g.streams, g.rank, g.genre) ).result
			// sql"""select * from $genres""".as[Genres]
		}
}