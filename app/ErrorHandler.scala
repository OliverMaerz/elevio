import java.io.IOException

import play.api.http.HttpErrorHandler
import play.api.mvc._
import play.api.mvc.Results._

import scala.concurrent._
import javax.inject.Singleton
import play.api.Logger
import play.api.libs.json.JsResultException

/**
  * Custom Errorhandler: Overwrites the ErrorHandler and creates nicer error pages
  * using the template error.scala.html in the views folder
  */
@Singleton
class ErrorHandler extends HttpErrorHandler {

  private val logger = Logger(this.getClass)

  def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    Future.successful(
      Status(statusCode)(views.html.error("Sorry, but we could not find the page you are looking for. (Error " + statusCode.toString + ")"))

    )
  }

  def onServerError(request: RequestHeader, e: Throwable): Future[Result] = {
    logger.error(e.getMessage)
    Future.successful(
      e match {
        case e: IOException =>
          // logger.error(e.getMessage)
          Status(503)(views.html.error("Sorry, we are having difficulties retrieving the data you requested.\nPlease try again later. (Error 503)"))
        case e: JsResultException =>
          Status(404)(views.html.error("Sorry, we we could not find the article/data you are looking for. (Error 404)"))
        case _ =>
          // InternalServerError ("Oooopsie, A server error occurred: " + e.getMessage)
          //Status(500)(views.html.error(e.getMessage))
          Status(500)(views.html.error("Ooops. We can currently not process the request. Please try again later. (Error 500)"))
          //ErrorHandler.super.

      }
    )
  }
}

