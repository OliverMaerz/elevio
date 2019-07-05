package controllers

import java.io.FileInputStream
import java.util.Properties
import javax.inject._

import akka.actor.ActorSystem
import play.api.Logger
import play.api.i18n.I18nSupport
import play.api.libs.ws._
import play.api.mvc._
import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

import models.{Article, ArticleList, Search}


class ArticleController2 @Inject()(ws: WSClient, val controllerComponents: ControllerComponents,
                                            ec: ExecutionContext, actorSystem: ActorSystem)(implicit exec: ExecutionContext)
  extends BaseController with I18nSupport {

  /* Create logger instance to log successful api requests and errors */
  private val logger = Logger(this.getClass)

  /* load config (api key and token from conf/api.conf) */
  private val properties: Properties = new Properties()
  try {
    /* Loading api key and token from /conf/api.conf file to authenticate requests sent to Elevio */
    properties.load(new FileInputStream("conf/api.conf"))
  } catch {
    case exp: Exception =>
      logger.error("Error loading key and token configuration file api.conf")
  }
  private val key: String = properties.getProperty("key")
  private val token: String = "Bearer " + properties.getProperty("token")
  logger.info("Loaded API key: "+key)
  logger.info(token)


  /* Get a list of articles and map response to Articles class from models*/
  def getArticles(page: Int): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    makeAPICall("https://api.elev.io/v1/articles", page, "").map {
      apiResponse => Ok(views.html.articles {
        // process the whole response and pass as ArticleList
        apiResponse.json.as[ArticleList]
      })
    }
  }


  /* Get data for the article with id given and map response to Article class from models */
  def getArticle(id: Long): Action[AnyContent] = Action.async {
    makeAPICall("https://api.elev.io/v1/articles/"+id.toString, 1, "").map {
      apiResponse => Ok(views.html.article {
        // process child of "article" part of the JSON response and map to Article
        (apiResponse.json \ "article").as[Article]
      })
    }
  }


  def searchArticlesForm: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.articleSearchForm(Search.searchForm))
  }


  /* Search Articles by keywords */
  def searchArticles(keyWords: String, page: Int): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    makeAPICall("https://api.elev.io/v1/articleSearch/en", page, keyWords).map {
      apiResponse => Ok(views.html.articleSearchResults {
        // process the whole response and pass as ArticleList
        apiResponse.json.as[ArticleList]
      })
    }
  }


  /* Make the actual call to the Elevio API and return the JSON response */
  def makeAPICall(url: String, page: Int, keyWords: String): Future[WSResponse] = {
    logger.info("Page number received :" + page)
    val call =  ws.url(url)
      .addHttpHeaders("x-api-key" -> key)
      .addHttpHeaders("Authorization" -> token)
      .addQueryStringParameters("page_size" -> "5")
      .addQueryStringParameters("rows" -> "5")
      .addQueryStringParameters("page" -> page.toString)
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

