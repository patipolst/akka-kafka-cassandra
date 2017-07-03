package http.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object PingRoute {
  val route: Route = pathPrefix("ping") {
    pathEndOrSingleSlash {
      get {
        complete("pong")
      }
    }
  }
}
