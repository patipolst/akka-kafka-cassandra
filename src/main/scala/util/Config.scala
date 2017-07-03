package util

import pureconfig.loadConfigOrThrow

trait Config {

  case class Config(http: HttpConfig, cassandra: CassandraConfig)
  case class HttpConfig(interface: String, port: Int)
  case class CassandraConfig(host: Seq[String], keyspace: String, username: String, password: String)

  val config: Config = loadConfigOrThrow[Config]
}

