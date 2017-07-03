package util

import entities._
import com.wix.accord.dsl._
import com.wix.accord._
import ViolationBuilder._
import com.wix.accord.transform.ValidationTransform.TransformedValidator
import org.joda.time.DateTime

trait ValidationMethods {
  def oneOf[ T <: AnyRef ]( options : T* ): Validator[ T ] =
    new NullSafeValidator[ T ](
      test    = options.contains,
      failure = _ -> s"is not one of (${ options.mkString( "," ) })"
    )

  def letter[ T <: String ]: Validator[ T ] =
    new NullSafeValidator[ T ](
      test    = _.forall(_.isLetter),
      failure = _ -> s"must be letter"
    )

  def digit[ T <: String ]: Validator[ T ] =
    new NullSafeValidator[ T ](
      test    = _.forall(_.isDigit),
      failure = _ -> s"must be digit"
    )

  def validYear[ T <: Int ]: Validator[ T ] =
    new BaseValidator[ T ](
      test    = (1800 to DateTime.now().getYear).contains,
      failure = _ -> s"must be in valid year format"
    )
}

object Validator extends ValidationMethods {
  implicit val songValidator: TransformedValidator[Song] = validator[Song] { song =>
    song.artist is notEmpty
//    song.id is
    song.title is notEmpty
    song.album is notEmpty
    song.year is validYear
  }
}
