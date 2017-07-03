package http

import akka.http.scaladsl.server.Directive1
import akka.http.scaladsl.server.Directives.{complete, provide}
import com.wix.accord.Validator
import util.Helper

trait CommonDirectives extends Helper {
  def validate[T](model: T)(implicit validator: Validator[T]): Directive1[T] = validateModel(model) match {
    case Nil => provide(model)
    case errors => complete(errors.mkString(", "))
  }
}