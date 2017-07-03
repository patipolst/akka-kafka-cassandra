package http.routes

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Directive1, Route}
import com.outworkers.phantom.ResultSet
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import entities.Song
import http.CommonDirectives
import io.circe.Json
import io.circe.generic.auto._
import io.circe.syntax._
import services.SongService
import util.Validator.songValidator

import scala.concurrent.Future
import scala.util.{Failure, Success}

class SongRoute(songService: SongService) extends CommonDirectives {

  val route: Route = pathPrefix("songs") {
    pathEndOrSingleSlash {
      (get & parameter("artist".as[String].?)) { artistOption =>
        wrapResponse {
          artistOption match {
            case Some(artist) => songService.findByArtist(artist)
            case None => songService.findAll
          }
        }
      } ~
        (post & entity(as[Json])) { json =>
          asSong(json) { song =>
            wrapResponse {
              songService.store(song)
            }
          }
        } ~
        (delete & entity(as[Json])) { json =>
          asSong(json) { song =>
            wrapResponse {
              songService.delete(song)
            }
          }
        }
    } ~
      pathPrefix(JavaUUID) { id =>
        pathEndOrSingleSlash {
          get {
            wrapResponse {
              songService.findById(id)
            }
          }
        }
      }
  }

  private def asSong(json: Json): Directive1[Song] = json.normalize.as[Song] match {
    case Right(song) => validate(song)
    case Left(_) => complete("Invalid song format!")
  }

  private def wrapResponse[T](fn: Future[T]): Route = onComplete(fn) {
    case Success(songs: Seq[_]) if songs.nonEmpty => complete(songs.collect { case s: Song => s }.asJson)
    case Success(song: Song) => complete(song.toString)
    case Success(Some(song: Song)) => complete(song.asJson)
    case Success(Nil | None) => complete((NotFound, "Songs not found"))
    case Success(r: ResultSet) if r.wasApplied() => complete(r.toString)
    case Failure(ex) => complete(ex.getMessage)
  }
}