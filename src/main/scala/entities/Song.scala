package entities

import java.util.UUID

case class Song(
                 id: UUID,
                 title: String,
                 album: String,
                 year: Int,
                 artist: String
               )