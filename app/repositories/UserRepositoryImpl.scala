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
        s"""INSERT INTO ${UserRepoConst.USER_TABLE} (${UserRepoConst.ALL_FIELDS}) VALUES ({username},{password},{member})"""

      SQL(sqlProperty)
        .on('username -> user.username, 'password -> user.password, 'member -> user.member)
        .executeInsert()

    }}
  })

  override def getUser(username: String): Option[User] = db.withConnection{implicit c => {
      val sqlUser = s"""SELECT * FROM ${UserRepoConst.USER_TABLE} WHERE ${UserRepoConst.USRNAME} = {username}"""

      SQL(sqlUser)
       .on('username -> username)
       .as(UserRepositoryImpl.userParser.*).headOption
    }
  }
}


object UserRepositoryImpl {
  import anorm.SqlParser.{ str, int, bool }

  def userParser:RowParser[User] = {
    for {
      id <- int(UserRepoConst.ID)
      username <- str(UserRepoConst.USRNAME)
      password <- str(UserRepoConst.PWD)
      member <- bool(UserRepoConst.MEMBER)
    } yield {User(
      id = id,
      username = username,
      password = password,
      member = member
    )}
  }

}
