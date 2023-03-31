package models.table

import models.Genres
import models.Types.{Artist, Genre, GenreId, Rank, Region, Streams, Title}
import slick.jdbc.PostgresProfile.api._

class GenresTable(tag: Tag) extends Table[Genres](_tableTag = tag, _tableName = "genres") {

	def id = column[GenreId]("id", O.PrimaryKey)
	def title = column[Title]("title")
	def artist = column[Artist]("artist")
	def region = column[Region]("region")
	def streams = column[Streams]("streams")
	def rank = column[Rank]("ranking")
	def genre = column[Genre]("genre")

	def * = (id, title, artist, region, streams, rank, genre) <> (Genres.tupled, Genres.unapply)
}
