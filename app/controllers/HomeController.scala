package controllers

import javax.inject.Inject
import play.api.mvc._

class HomeController @Inject()(
                               cc: MessagesControllerComponents,
                               userController: UserController
                             ) extends MessagesAbstractController(cc) {

  private val submit = routes.UserController.validateUser()

  def index = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.index( userController.userForm, submit))
  }

}
