package services


import com.outworkers.phantom.dsl._
import databases.SongDatabase
import entities.Song

import scala.concurrent.Future

class SongService(db: SongDatabase) {

  import db._

  createAsync

  def store(song: Song): Future[ResultSet] =
    for {
      byId <- songs.store(song).future()
      byArtist <- songsByArtist.store(song).future()
    } yield byArtist

  //
  //  def store(song: Song): Future[ResultSet] = {
  //    for {
  //      byId <- songs.store(song)
  //      byArtist <- songsByArtist.store(song)
  //    } yield byArtist
  //  }

  def findAll: Future[List[Song]] = songs.findAllSongs

  def findById(id: UUID): Future[Option[Song]] = songs.findSongById(id)

  def findByArtist(artist: String): Future[List[Song]] = songsByArtist.findSongsByArtist(artist)

  def delete(song: Song): Future[ResultSet] =
    for {
      byId <- songs.deleteById(song.id)
      byArtist <- songsByArtist.deleteByArtistAndId(song.artist, song.id)
    } yield byArtist
}