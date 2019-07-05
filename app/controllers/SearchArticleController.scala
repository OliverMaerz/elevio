package controllers

import java.io.FileInputStream
import java.util.Properties

import akka.actor.ActorSystem
import javax.inject._
import models.{Article, ArticleList, Search, SearchList}
import play.api.Logger
import play.api.i18n.I18nSupport
import play.api.libs.ws._
import play.api.mvc._

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}


class SearchArticleController @Inject()(ws: WSClient, val controllerComponents: ControllerComponents,
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
  private val numArticles: String = properties.getProperty("num_articles", "5")


  def searchArticlesForm: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.articleSearchForm(Search.searchForm))
  }


  /* Search Articles by keywords */
  def searchArticles(keyWords: String, page: Int): Action[AnyContent] = Action.async { implicit request =>
    makeAPICall("https://api.elev.io/v1/search/en", page, keyWords).map {
      apiResponse => Ok(views.html.articleSearchResults {
        // process the whole response and pass as ArticleList
        apiResponse.json.as[SearchList]
      })
    }
  }


  /* Make the actual call to the Elevio API and return the JSON response */
  def makeAPICall(url: String, page: Int, keywords: String): Future[WSResponse] = {
    logger.info("Page number received :" + page)
    val call =  ws.url(url)
      .addHttpHeaders("x-api-key" -> key)
      .addHttpHeaders("Authorization" -> token)
      .addQueryStringParameters("rows" -> numArticles)
      .addQueryStringParameters("page" -> page.toString)
      .addQueryStringParameters("query" -> keywords)
      .withRequestTimeout(10000.millis).get()


    call.onComplete{
      case Success(res) =>
        logger.info("API request successful")
        logger.info("Called API" + call.toString)

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

