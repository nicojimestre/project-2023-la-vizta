package controllers

import models.SpotifyCharts
import models.dao.SpotifyChartsDAOImpl
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json.{JsPath, Json, Writes}
import play.api.mvc._

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

//trait MyExecutionContext extends ExecutionContext
//
//class MyExecutionContextImpl @Inject()(system: ActorSystem)
//    extends CustomExecutionContext(system, "my.executor")
//        with MyExecutionContext

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class SpotifyController @Inject()(val dbConfigProvider: DatabaseConfigProvider, val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext) extends BaseController {

    val dao = new SpotifyChartsDAOImpl(dbConfigProvider)

    implicit val spotifyChartsWrites: Writes[SpotifyCharts] = (
        (JsPath \ "title").write[String] and
        (JsPath \ "rank").write[Int] and
        (JsPath \ "date").write[String] and
        (JsPath \ "artist").write[String] and
        (JsPath \ "url").write[String] and
        (JsPath \ "region").write[String] and
        (JsPath \ "chart").write[String] and
        (JsPath \ "trend").write[String] and
        (JsPath \ "streams").write[Int]
    ) (unlift(SpotifyCharts.unapply))

    def get100first: Action[AnyContent] = Action.async {
        dao.getFamousTitles.map( r => Ok(Json.toJson(r)) )
    }

    def getRecentTopViral50ByCountry: Action[AnyContent] = Action.async { req =>
        req.getQueryString("country") match {
            case Some(country) => dao.getRecentTopViral50ByCountry(country)
                .map( r => {
                    Ok(Json.toJson(r))
                } )
            case None => Future { Ok("err: need attr country") }
        }
    }

    def getMostStreamedPerCountry: Action[AnyContent] = Action.async {
        dao.getMostStreamedPerCountry
            .map(r => {
                Ok(Json.toJson(r))
            })
    }

//    def test = Action.async( {
//        dao.test().map( r => {
//            Ok(Json.toJson(r))
//        })
//    })
}
