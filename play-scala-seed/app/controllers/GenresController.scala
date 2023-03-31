package controllers

import models.Genres
import models.Types.{Artist, Genre, GenreId, Rank, Region, Streams, Title}
import models.dao.{GenresDAOImpl, SpotifyChartsDAOImpl}
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json.{JsPath, Json, Writes}
import play.api.mvc._
import slick.jdbc.GetResult

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps

@Singleton
class GenresController @Inject()(val dbConfigProvider: DatabaseConfigProvider, val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext) extends BaseController {


    case class ResGenres(
                                  tracksPerRegion: Map[Region, Seq[Genres]],
                                  topGenresPerRegion: Map[Region, Seq[Genre]]
                                )

    val dao = new GenresDAOImpl(dbConfigProvider)

    implicit val genresWrites: Writes[Genres] = (
        (JsPath \ "id").write[GenreId] and
        (JsPath \ "title").write[Title] and
        (JsPath \ "artist").write[Artist] and
        (JsPath \ "region").write[Region] and
        (JsPath \ "streams").write[Streams] and
        (JsPath \ "ranking").write[Rank] and
        (JsPath \ "genre").write[Genre]
    )(unlift(Genres.unapply))

    implicit val resGenresWrites: Writes[ResGenres] = (
        (JsPath \ "tracksPerRegion").write[Map[Region, Seq[Genres]]] and
        (JsPath \ "topGenresPerRegion").write[Map[Region, Seq[Genre]]]
    )(unlift(ResGenres.unapply))

//    def func = Action.async {
//        dao.getFunc().map( res => {
//            val json = Json.toJson(res)
//            Ok( json )
//        } )
//    }

    def getGenres: Action[AnyContent] = Action.async {
        dao.getGenres.map( r => {
            val tracksPerRegion = r.map(Genres tupled)
              .groupBy(_.region)

            val topGenresPerRegion = tracksPerRegion
              .view
              .mapValues(_.groupBy(_.genre))
              .mapValues( m => m.view
                .mapValues(_.foldLeft(0)((acc, cur) => acc + cur.streams))
                .toSeq
                .sortBy(-_._2)
                .map(_._1)
              )
              .toMap

            val res = ResGenres(tracksPerRegion, topGenresPerRegion)
            Ok(Json.toJson(res))
        } )
    }
}
