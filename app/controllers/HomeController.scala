package controllers

import javax.inject._
import play.api.i18n.I18nSupport
import play.api.mvc._

/**
 * Controller to handle HTTP requests to the application's home page
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with I18nSupport {

  /**
   * Action to render an HTML page with a welcome message.
   */
  def index: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.index("Welcome to the Elevio Demo", "Please click on any of the menu items in the upper right corner."))
  }

}
