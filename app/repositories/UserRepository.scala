package repositories

import models.User

import scala.concurrent.Future

trait UserRepository {

  def addUser(user: User): Future[Unit]
}
