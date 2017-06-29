package util

import pureconfig.loadConfigOrThrow

trait Config {

  case class Config(http: HttpConfig)
  case class HttpConfig(interface: String, port: Int)

  val config: Config = loadConfigOrThrow[Config]
}

