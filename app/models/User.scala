package models

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._


case class User (
                  id: Int = 0,
                  username: String,
                  password: String,
                  userType: String
                )

object User {
  implicit val readsUser: Reads[User] = (
    (JsPath \ "id").read[Int] or Reads.pure(0) and
      (JsPath \ "username").read[String] and
      (JsPath \ "password").read[String] and
      (JsPath \ "userType").read[String]
    )(User.apply _)

  implicit val writesUser: Writes[User] = (
    (JsPath \ "id").write[Int] and
      (JsPath \ "username").write[String] and
      (JsPath \ "password").write[String] and
      (JsPath \ "userType").write[String]
    )(unlift(User.unapply))
}

