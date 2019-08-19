package models

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

case class WallInput (
                       id: Int,
                       userName: String,
                       _message: String,
                       _type: String
                     )

object WallInput {
  implicit val readsWallInput: Reads[WallInput] = (
    (JsPath \ "id").read[Int] or Reads.pure(0) and
      (JsPath \ "userName").read[String] and
      (JsPath \ "message").read[String] and
      (JsPath \ "type").read[String].map(m => if(m.toUpperCase.trim == "SAY"){"SAY"} else {"THOUGHT"})
    )(WallInput.apply _)

  implicit val writesWallInput: Writes[WallInput] = (
    (JsPath \ "id").write[Int] and
      (JsPath \ "userName").write[String] and
      (JsPath \ "message").write[String] and
      (JsPath \ "type").write[String]
    )(unlift(WallInput.unapply))
}