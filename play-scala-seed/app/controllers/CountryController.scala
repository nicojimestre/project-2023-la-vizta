package controllers

import models.dao.CountryDAOImpl
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.Json
import play.api.mvc._

import javax.inject._
import scala.concurrent.ExecutionContext

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class CountryController @Inject()(val dbConfigProvider: DatabaseConfigProvider, val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext) extends BaseController {

    val dao = new CountryDAOImpl(dbConfigProvider)

//    implicit val countryWrites: Writes[Country] = (
//        (JsPath \ "id")  .write[Int]    and
//        (JsPath \ "name").write[String] and
//        (JsPath \ "geom").write[String]
//    )(unlift(Country.unapply))

    def getCountriesGeometry: Action[AnyContent] = Action.async( {
        dao.getCountriesGeometry
            .map( c => Ok(Json.toJson(c)) )
    })
}
