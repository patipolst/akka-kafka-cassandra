
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import databases.SongDatabase
import databases.connector.Connector
import http.HttpService
import services.SongService
import util.Config

object Main extends App with Config {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executor = system.dispatcher

  val songDatabase = new SongDatabase(Connector.defaultConnector)
  val songService = new SongService(songDatabase)
  //  val songService = new SongService(DefaultDatabase)

  val httpService = new HttpService(songService)

  Http().bindAndHandle(httpService.routes, config.http.interface, config.http.port)
}
