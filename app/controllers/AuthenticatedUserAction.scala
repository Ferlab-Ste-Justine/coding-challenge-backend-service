package controllers

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import play.api.mvc.Results._
import play.api.mvc._

class AuthenticatedUserAction @Inject()(parser: BodyParsers.Default)(implicit ec: ExecutionContext)
  extends ActionBuilderImpl(parser) {

  override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
    request.session.get(models.Global.SESSION_USER) match {
      case None =>
        Future.successful(Forbidden("You are not logged in"))
      case Some(_) =>
        block(request)
    }
  }
}



