package repositories

import models.WallInput

import scala.concurrent.Future

trait UserInputsRepository {
  def addUserInput(username: String)(wallInput: WallInput): Future[Unit]
  def getUserInputs(username: String): Future[List[WallInput]]
}
