package controllers

import akka.actor.ActorSystem
import javax.inject._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future, Promise}

/**
 * This controller handles all article related tasks
 *
 * @param cc standard controller components
 * @param actorSystem `ActorSystem`'s `Scheduler` to run code after a delay.
 * @param exec `ExecutionContext` to execute asynchronous code.
 */
@Singleton
class ArticlesAsyncController @Inject()(cc: ControllerComponents, actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends AbstractController(cc) {

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
}
