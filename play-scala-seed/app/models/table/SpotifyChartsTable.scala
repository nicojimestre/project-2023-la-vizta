package models.table

import models.SpotifyCharts
import models.Types._
import slick.jdbc.PostgresProfile.api._

class SpotifyChartsTable(tag: Tag) extends Table[SpotifyCharts](_tableTag = tag, _tableName = "spotify_charts") {

	def title = column[Title]("title")
	def rank = column[Rank]("rank")
	def date = column[Date]("date")
	def artist = column[Artist]("artist")
	def url = column[Url]("url")
	def region = column[Region]("region")
	def chart = column[Chart]("chart")
	def trend = column[Trend]("trend")
	def streams = column[Streams]("streams")

	def * = (title, rank, date, artist, url, region, chart, trend, streams) <> (SpotifyCharts.tupled, SpotifyCharts.unapply)
}
