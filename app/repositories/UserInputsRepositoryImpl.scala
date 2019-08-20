package repositories

import javax.inject.Inject

import anorm._
import models.WallInput
import play.api.db.{Database, NamedDatabase}

import scala.concurrent.{ExecutionContext, Future, blocking}

class UserInputsRepositoryImpl @Inject() (
                                     @NamedDatabase("default") db: Database
                                   )(implicit ec: ExecutionContext) extends UserInputsRepository {

  override def addUserInput(username: String)(wallInput: WallInput): Future[Unit] = Future(blocking {
    db.withConnection{implicit c => {
      val sqlProperty =
        s"""INSERT INTO ${UserRepoConst.USER_INPUTS_TABLE} (${UserRepoConst.ALL_FIELDS_USERINPUTS}) VALUES ({username},{message},{typemessage})"""
      SQL(sqlProperty)
        .on('username -> username, 'message -> wallInput._message, 'typemessage -> wallInput._type)
        .executeInsert()
    }}
  })

    override def getUserInputs(username: String): Future[List[WallInput]] = Future(db.withConnection{implicit c => {
      val sqlUser = s"""SELECT * FROM ${UserRepoConst.USER_TABLE} WHERE ${UserRepoConst.USRNAME} = {username}"""

      SQL(sqlUser)
        .on('username -> username)
        .as(UserInputsRepositoryImpl.userInputParser.*)
    }})
}

object UserInputsRepositoryImpl {
  import anorm.SqlParser.{ str, int }

  def userInputParser:RowParser[WallInput] = {
    for {
    id <- int(UserRepoConst.ID)
    _message <- str(UserRepoConst.MESSAGE)
    _type <- str(UserRepoConst.TYPEMESSAGE)
  } yield {WallInput(
    id = id,
    _message = _message,
      _type = _type
    )}
  }
}




