package controllers

import java.io.FileInputStream
import java.util.Properties

import javax.inject.Inject

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._
import play.api.mvc._
import play.api.libs.ws._
import akka.actor.ActorSystem
import models.{Article, ArticleList}
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import play.api.Logger

import scala.util.{Failure, Success}

class ArticleController2 @Inject()(ws: WSClient, val controllerComponents: ControllerComponents,
                                   ec: ExecutionContext, actorSystem: ActorSystem)(implicit exec: ExecutionContext)
  extends BaseController {

  /* Create logger instance to log successful api requests and errors */
  private val logger = Logger(this.getClass)

  /* load config (api key and token) */
  var properties: Properties = new Properties()
  try {
    /* Loading api key and token from /conf/api.conf file to authenticate requests sent to elevio */
    properties.load(new FileInputStream("conf/api.conf"))
    val key: String = properties.getProperty("key")
    val token: String = "Bearer " + properties.getProperty("token")
  } catch {
    case exp: Exception =>
      logger.error("Error retrieving articles")
  }

  def format(date: DateTime, pattern: String = "yyyy-MM-dd") = DateTimeFormat.forPattern(pattern).print(date)

  /* Get a list of articles and map response to Articles class from models*/
  def getArticles: Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    makeAPICall("https://api.elev.io/v1/articles").map {
      apiResponse => Ok(views.html.articles {
        // process the whole response and map to ArticleList
        apiResponse.json.as[ArticleList]
      })
    }
  }

  /* Get data for the article with id given and map response to Article class from models */
  def getArticle(id: Long): Action[AnyContent] = Action.async {
    makeAPICall("https://api.elev.io/v1/articles/"+id.toString).map {
      apiResponse => Ok(views.html.article {
        // process child of "article" part of the JSON response and map to Article
        (apiResponse.json \ "article").as[Article]
      })
    }
  }

  /* Make the actual call to the elevio API and return the JSON response */
  def makeAPICall(url: String): Future[WSResponse] = {
    val call =  ws.url(url)
                  .addHttpHeaders("x-api-key" -> "a59c720dc2cbd90048c027a63acae560")
                  .addHttpHeaders("Authorization" -> "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL2FwcC5lbGV2LmlvIiwic3ViIjoiNWQwZDZjN2FkMmMzYiIsImV4cCI6MzEzODQ1MzQ0MCwiaWF0IjoxNTYxNjUzNDQwLCJqdGkiOiJlaWJsMmplMThqbWQ5bXFyOGFwMmk1dmk4YWNuaGpkOCIsCiAgInVzZXJOYW1lIiA6ICJvbUBvbGl2ZXJtYWVyei5jb20iLAogICJ1c2VySWQiIDogMTc2MTMsCiAgInNjb3BlIiA6IFsgInJlYWQ6Y29udGV4dHVhbCIsICJyZWFkOmNhcmQiLCAicmVhZDphcnRpY2xlIiBdCn0.coI7P7tTo78VPzxZgVp805aAKFZJSfdKTWNgiYdd9WQ")
                  //.addQueryStringParameters("param" -> "value")
                  .withRequestTimeout(10000.millis).get()

    call.onComplete{
      case Success(res) =>
        logger.info("API request successful")
      case Failure(e) =>
        logger.error("Error during API request")
    }
    call
  }
}

/*

web pages of interest

json to model examples
https://pedrorijo.com/blog/scala-json/

https://www.playframework.com/documentation/2.7.x/ScalaJsonCombinators

api calls
https://www.playframework.com/documentation/2.7.x/ScalaWS

async
https://www.playframework.com/documentation/2.7.x/ScalaAsync

akka
https://www.toptal.com/scala/concurrency-and-fault-tolerance-made-easy-an-intro-to-akka


 */

