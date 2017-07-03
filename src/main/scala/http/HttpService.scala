package http

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import http.routes.{PingRoute, SongRoute}
import services.SongService

class HttpService(songService: SongService) {

  val songRouter = new SongRoute(songService)

  val routes: Route = PingRoute.route ~ songRouter.route

}
