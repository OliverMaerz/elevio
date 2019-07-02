package models

import javax.inject.Inject
import java.io.FileInputStream
import java.util.Properties

import akka.parboiled2.RuleTrace.Action
import play.api.Logger
import play.api.libs.ws._
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global

case class Art(title: String, id: Int)

class Article @Inject()(ws: WSClient, ec: ExecutionContext) {

  val articleUrl = "https://api.elev.io/v1/articles"
  val logger = Logger(this.getClass)

  object Article {


    private var articleList: List[Article] = List()

    def list: List[Article] = {
      articleList
    }

    def get(articleId: Int): Article = {
      articleList(articleId)
    }

    // https://www.playframework.com/documentation/2.7.x/ScalaWS
    // https://www.playframework.com/documentation/2.7.x/ScalaAsync

    def retrieveArticle() : Unit = {
      //articleId: Int
      var properties: Properties = new Properties()
      try {
        /* Loading api key and token from /conf/api.conf file to authenticate requests sent to elevio */
        properties.load(new FileInputStream("conf/api.conf"))
        val key: String = properties.getProperty("key")
        val token: String = "Bearer " + properties.getProperty("token")

        /* Build the request to then send to elevio */
        val request: WSRequest = ws.url(articleUrl)
        val apiRequest : WSRequest =
          request
            .addHttpHeaders("key" -> key)
            .addHttpHeaders("token" -> token)

        // val response: Future[WSResponse] = apiRequest.get()
        /*val futureResult: Future[String] = apiRequest.get().map { response =>
          (response.json \ "articles" \ "title").as[String]
        }*/

        implicit val articleReads: Reads[Art] = Json.reads[Art]

        val futureResult: Future[JsResult[Art]] = apiRequest.get().map { response =>
          (response.json \ "articles").validate[Art]
        }

        logger.info("Retrieving articles via API ")
        //return articleList
      } catch {
        case exp: Exception =>
          logger.error("Error retrieving articles")
      }
    } // def retrieveArticle

    /*
    def asyncArticle = Action.async {

    }

     */

  } // object
} // class
