package models

import java.util.UUID

import com.outworkers.phantom.dsl._
import entities.Song

import scala.concurrent.Future

abstract class Songs extends Table[Songs, Song] {

  object id extends UUIDColumn with PartitionKey {
    override lazy val name = "song_id"
  }

  object title extends StringColumn

  object album extends StringColumn

  object year extends IntColumn

  object artist extends StringColumn

  def findAllSongs: Future[List[Song]] = {
    select.fetch()
  }

  def findSongById(id: UUID): Future[Option[Song]] = {
    select
      .where(_.id eqs id)
      .one()
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

  def deleteById(id: UUID): Future[ResultSet] = {
    delete
      .where(_.id eqs id)
      .future()
  }
}