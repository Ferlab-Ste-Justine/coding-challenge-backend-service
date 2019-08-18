package controllers

import javax.inject.Inject

import models.{Global, User}

import play.api.mvc.{Action, _}
import play.api.libs.json.{JsValue, Json}
import repositories.UserRepository

import scala.concurrent.Future


class UserController @Inject()(
  userRepository: UserRepository
)(cc: ControllerComponents) extends AbstractController(cc) {

  def validateUser: Action[JsValue] = Action.async(parse.tolerantJson) { implicit request =>
    println("ADRIAN")
    println(request.session.get(Global.SESSION_USER))
    Future.successful(Ok.withSession(Global.SESSION_USER -> "ADRIAN222"))

  }

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
}
