package databases.connector

import com.outworkers.phantom.connectors.{CassandraConnection, ContactPoint, ContactPoints}
import util.Config

object Connector extends Config {
  lazy val defaultConnector: CassandraConnection = ContactPoint.local.noHeartbeat().keySpace(config.cassandra.keyspace)

  lazy val remoteConnector: CassandraConnection = ContactPoints(config.cassandra.host)
    .withClusterBuilder(_.withCredentials(config.cassandra.username, config.cassandra.password))
    .keySpace(config.cassandra.keyspace)

  lazy val testConnector: CassandraConnection = ContactPoint.embedded.noHeartbeat().keySpace("testing_keyspace")
}
