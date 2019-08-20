package object repositories {
  @javax.inject.Singleton
  object UserRepoConst {

    val ID = "id"
    val USRNAME = "username"
    val PWD = "password"
    val MESSAGE = "message"
    val TYPEMESSAGE = "typeMessage"

    val USER_TABLE = "wall.user"
    val USER_INPUTS_TABLE = "wall.userinputs"

    val ALL_FIELDS_USER: String =  s"$USRNAME, $PWD"
    val ALL_FIELDS_USERINPUTS: String =  s"$USRNAME, $MESSAGE, $TYPEMESSAGE"
  }
}
