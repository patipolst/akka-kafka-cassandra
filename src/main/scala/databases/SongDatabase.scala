package databases

import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.dsl._
import models.{Songs, SongsByArtist}

class SongDatabase(
                   override val connector: CassandraConnection
                 ) extends Database[SongDatabase](connector) {
  object songs extends Songs with Connector
  object songsByArtist extends SongsByArtist with Connector
}

//class SongDatabase(
//                   override val connector: CassandraConnection
//                 ) extends Database[SongDatabase](connector) {
//  object songs extends Songs with Connector
//  object songsByArtist extends SongsByArtist with Connector
//}
//
//object DefaultDatabase extends SongDatabase(Connector.defaultConnector)
//
//object RemoteDatabase extends SongDatabase(Connector.remoteConnector)
//
//object TestDatabase extends SongDatabase(Connector.testConnector)
