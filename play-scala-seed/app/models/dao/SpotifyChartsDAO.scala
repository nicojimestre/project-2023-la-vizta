package models.dao

import models.Types._

import scala.concurrent.Future

trait SpotifyChartsDAO {
    def getFamousTitles: Future[Seq[String]]
    def getRecentTopViral50ByCountry(country: Region): Future[(Title, Artist, Url, Streams)]
    def getMostStreamedPerCountry: Future[Vector[(Region, Title, Date, Url, Streams)]]
}