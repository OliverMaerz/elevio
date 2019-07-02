package controllers

import akka.actor.ActorSystem
import javax.inject._
import play.api.mvc._
import play.api.libs.ws._
import play.api.libs.concurrent.CustomExecutionContext

import scala.concurrent.{ExecutionContext, Future, Promise}

trait MyExecutionContext extends ExecutionContext

class MyExecutionContextImpl @Inject()(system: ActorSystem)
  extends CustomExecutionContext(system, "my.executor")
  with MyExecutionContext

/**
 * This controller handles all article related tasks
 *
 * @param cc standard controller components
 * @param actorSystem `ActorSystem`'s `Scheduler` to run code after a delay.
 * @param exec `ExecutionContext` to execute asynchronous code.
 */
@Singleton
class ArticleController @Inject()(ws: WSClient, executionContext: ExecutionContext, cc: ControllerComponents, actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends AbstractController(cc) {

  /**
    * Creates an Action that returns retrieves a list of articles
    *
    */
  // def message = Action.async(getListOfArticles(0).map { msg => Ok(msg) })

  // private def getListOfArticles(page: Integer): String = {
  //  "Test"
  // }

  def getListOfArticles: Action[AnyContent] = Action {
    Ok(views.html.articles("Placeholder for List of Articles ..."))
  }
  /*
  def message: Action[AnyContent] = Action.async {
    getFutureMessage(1.second).map { msg => Ok(msg) }
  }

  private def getFutureMessage(delayTime: FiniteDuration): Future[String] = {
    val promise: Promise[String] = Promise[String]()
    actorSystem.scheduler.scheduleOnce(delayTime) {
      promise.success("Hi!")
    }(actorSystem.dispatcher) // run scheduled tasks using the actor system's dispatcher
    promise.future
  }


  val responseFuture = WS.url("http://api.server.com/rest/")
    .withAuth("test", "test", com.ning.http.client.Realm.AuthScheme.BASIC)
    .get()

  val resultFuture = responseFuture map { response =>
    response.status match {
      case 200 => Some(response.xml)
      case _ => None
    }
  }
  */
}

