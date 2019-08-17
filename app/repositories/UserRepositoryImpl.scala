package repositories

import javax.inject.Inject
import models.User
import play.api.db.{Database, NamedDatabase}
import anorm._
import scala.concurrent.{ExecutionContext, Future, blocking}

class UserRepositoryImpl @Inject() (
                                     @NamedDatabase("default") db: Database
                                   )(implicit ec: ExecutionContext) extends UserRepository {

  override def addUser(user: User): Future[Unit] = Future(blocking {
    db.withConnection{implicit c => {
      val sqlProperty =
        s"""INSERT INTO ${UserRepoConst.USER_TABLE} (${UserRepoConst.ALL_FIELDS}) VALUES ({username},{password})"""

      SQL(sqlProperty)
        .on('username -> user.username, 'password -> user.password)
        .executeInsert()

    }}
  })
}
