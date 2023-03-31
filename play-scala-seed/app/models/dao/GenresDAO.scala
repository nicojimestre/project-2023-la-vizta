package models.dao

import models.Types.{Artist, Genre, GenreId, Geometry, Rank, Region, Streams, Title}
import models.table.GenresTable

import scala.concurrent.Future

trait GenresDAO {
	def getGenres: Future[Seq[(GenreId, Title, Artist, Region, Streams, Rank, Genre)]]
}