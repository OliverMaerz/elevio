package controllers

import javax.inject._
import play.api.mvc._

/**
 * Controller to handle HTTP requests to the application's home page
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Action to render an HTML page with a welcome message.
   */
  def index: Action[AnyContent] = Action {
    Ok(views.html.index("Welcome to the elevio Demo", "Placeholder text for front page"))
  }
}
