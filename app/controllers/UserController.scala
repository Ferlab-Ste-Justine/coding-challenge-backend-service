package controllers

import javax.inject.Inject

import models.{Global, User, WallInput}
import play.api.mvc.{Action, _}
import play.api.libs.json.{JsValue, Json}
import play.api.data._
import play.api.data.Forms._
import repositories.{UserInputsRepository, UserRepository}

import scala.concurrent.Future


class UserController @Inject()(
  userRepository: UserRepository,
  userInputsRepository: UserInputsRepository,
  authenticatedUserAction: AuthenticatedUserAction
                              )(cc: ControllerComponents) extends AbstractController(cc) {

  val userForm = Form(
    mapping(
      "id" -> ignored(0),
      "username" -> text,
      "password"  -> text
    )(User.apply)(User.unapply)
  )

  /** Validate that the request user is a registered member
    * receive a Form data and binds it to userForm
    */
  def validateUser: Action[AnyContent] = Action { implicit request =>

    val formValidationResult: Form[User] = userForm.bindFromRequest

    formValidationResult.data.get("username").flatMap(u => userRepository.getUser(u)) match {
      case Some(user) => if(formValidationResult.data.get("password").contains(user.password)) {
        Ok.withSession(Global.SESSION_USER -> user.username)
      } else {
        BadRequest("Wrong password")
      }
      case None =>
        Unauthorized("You are not a valid user")
    }
  }

  /** Add a new user
    * deserialize JSON into User and attempt to add it
    */
  def addUser(): Action[JsValue] = Action.async(parse.tolerantJson) { implicit request =>
    val entries = request.body.asOpt[User]

    entries match {
      case Some(u) =>
        userRepository.getUser(u.username) match {
          case Some(_) => Future.successful(BadRequest("User already exists"))
          case None =>
            userRepository.addUser(u)
            val json = Json.toJson(entries)
            Future.successful(Created(json))
        }
      case None => Future.successful(BadRequest("Invalid request"))
    }
  }

  /** Add a new message from an authenticated User
    * deserialize JSON into WallInput and attempts to add it
    */
  def addUserMessage(): Action[JsValue] = authenticatedUserAction(parse.tolerantJson) { implicit request =>
    val entries = request.body.asOpt[WallInput]

    entries match {
      case Some(w) =>
        request.session.get("username") match {
        case Some(username) =>
          userInputsRepository.addUserInput(username)(w)
          val json = Json.toJson(entries)
          Created(json)
        case None => BadRequest("You should not be here")
      }
      case None => BadRequest("Invalid request")
    }
  }

  /** Logout an authenticated User
    */
  def logout() = authenticatedUserAction { implicit request: Request[AnyContent] =>
    Ok("Logout successful").withNewSession
  }

}
