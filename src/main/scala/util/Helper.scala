package util

import com.wix.accord.Descriptions.{AccessChain, Generic, Indexed}
import com.wix.accord.{Failure => AccordFailure, Success => AccordSuccess, _}
import io.circe.parser._
import io.circe.{Json, Printer}

object Helper extends Helper

trait Helper extends Config {

  implicit class JsonHelper(json: Json) {
    def normalize: Json = {
      val trimString: PartialFunction[Json, Json] = {
        case value => value.mapString(_.trim)
      }
      val transform: PartialFunction[Json, Json] = trimString

      val jsonFields = json.hcursor.fields.getOrElse(Nil)
      jsonFields.foldLeft(json)((acc, field) =>
        acc.hcursor.downField(field).withFocus(transform).top match {
          case Some(result) => result
          case None => json
        }
      )
    }

    def validateKeys(reqFields: List[String]): List[String] = {
      val jsonFields = json.hcursor.fields.getOrElse(Nil)
      reqFields.diff(jsonFields).map(f => s"$f is required") match {
        case Nil => "Malformed Json" :: Nil
        case errors => errors
      }
    }

    def valueOf(key: String): String = json.findAllByKey(key).headOption.getOrElse("").toString

    def dropNullKeys: Json =
      parse(Printer(
        preserveOrder = true,
        dropNullKeys = true,
        indent = "  "
      ).pretty(json)).getOrElse(Json.Null)
  }

  def validateModel[T](model: T)(implicit validator: Validator[T]): List[String] =
    validate(model) match {
      case AccordSuccess => Nil
      case AccordFailure(e) => e.map(x => x.description match {
        case AccessChain(Generic(field)) => s"$field: ${x.constraint}"
        case Indexed(_, AccessChain(Generic(field))) => s"$field: ${x.constraint}"
        case _ => s"${e.map(_.value).mkString}: ${x.constraint}"
      }).toList
    }
}
