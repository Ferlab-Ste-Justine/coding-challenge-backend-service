package object repositories {
  @javax.inject.Singleton
  object UserRepoConst {

    val ID = "id"
    val USRNAME = "username"
    val PWD = "password"

    val USER_TABLE = "wall.user"

    val ALL_FIELDS:String =  s"$USRNAME, $PWD"
  }
}
