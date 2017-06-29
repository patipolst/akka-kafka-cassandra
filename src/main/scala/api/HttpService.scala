package api

import akka.http.scaladsl.server.Route
import api.routes.PingRoute

class HttpService() {

  val routes: Route = PingRoute.route

}
