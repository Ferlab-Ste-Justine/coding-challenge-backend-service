package models

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

case class WallInput (
                       id: Int,
                       user: String,
                       thought: Option[String],
                       say: Option[String]
                     )

object WallInput {
  implicit val readsWallInput: Reads[WallInput] = (
    (JsPath \ "id").read[Int] or Reads.pure(0) and
      (JsPath \ "user").read[String] and
      (JsPath \ "thought").readNullable[String] and
      (JsPath \ "say").readNullable[String]
    )(WallInput.apply _)

  implicit val writesWallInput: Writes[WallInput] = (
    (JsPath \ "id").write[Int] and
      (JsPath \ "user").write[String] and
      (JsPath \ "thought").writeNullable[String] and
      (JsPath \ "say").writeNullable[String]
    )(unlift(WallInput.unapply))
}