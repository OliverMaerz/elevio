package controllers

import javax.inject.Inject

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._
import play.api.mvc._
import play.api.libs.ws._
import akka.actor.ActorSystem
import play.api.Logger

import scala.util.{Failure, Success}

case class Person(title: String, id: Int)

class ArticleController2 @Inject()(ws: WSClient, val controllerComponents: ControllerComponents,
                                   ec: ExecutionContext, actorSystem: ActorSystem)(implicit exec: ExecutionContext)
  extends BaseController {

  val logger = Logger(this.getClass)

  def getList2: Action[AnyContent] = Action.async {
    makeAPICall("https://api.elev.io/v1/articles/1").map { msg => Ok(views.html.articles((msg.json \ "article" \ "title").as[String])) }
  }

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

api calls
https://www.playframework.com/documentation/2.7.x/ScalaWS

async
https://www.playframework.com/documentation/2.7.x/ScalaAsync

akka
https://www.toptal.com/scala/concurrency-and-fault-tolerance-made-easy-an-intro-to-akka


 */

