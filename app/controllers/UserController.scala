package controllers

import javax.inject._

import models.User
import play.api.data.Form
import play.api.mvc._
import play.api.data.Forms.{mapping, nonEmptyText}
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.Future

@Singleton
class UserController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def validateUser: Action[JsValue] = Action.async(parse.tolerantJson) { implicit request =>
    println("ADRIAN")
    Future.successful(Ok)

  }



}
