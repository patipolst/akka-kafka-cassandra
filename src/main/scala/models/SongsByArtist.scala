package models

import java.util.UUID

import com.outworkers.phantom.dsl._
import entities.Song

import scala.concurrent.Future

abstract class SongsByArtist extends Table[SongsByArtist, Song] {

  object artist extends StringColumn with PartitionKey

  object id extends UUIDColumn with ClusteringOrder {
    override lazy val name = "song_id"
  }

  object title extends StringColumn

  object album extends StringColumn

  object year extends IntColumn

  override lazy val tableName = "songs_by_artist"

  def findSongsByArtist(artist: String): Future[List[Song]] = {
    select
      .where(_.artist eqs artist)
      .fetch()
  }

  //  def store(song: Song): Future[ResultSet] = {
  //    insert
  //      .value(_.id, song.id)
  //      .value(_.title, song.title)
  //      .value(_.album, song.album)
  //      .value(_.year, song.year)
  //      .value(_.artist, song.artist)
  //      .future()
  //  }

  def deleteByArtistAndId(artist: String, id: UUID): Future[ResultSet] = {
    delete
      .where(_.artist eqs artist)
      .and(_.id eqs id)
      .future()
  }
}