
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import api.HttpService
import util.Config

object Main extends App with Config {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executor = system.dispatcher

  val httpService = new HttpService()

  Http().bindAndHandle(httpService.routes, config.http.interface, config.http.port)
}
