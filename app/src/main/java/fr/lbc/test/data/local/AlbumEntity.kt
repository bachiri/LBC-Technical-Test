package fr.lbc.test.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.lbc.test.data.Album

@Entity
class AlbumEntity(
    @ColumnInfo(name = "albumId") val albumId: Int,
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "thumbnailUrl") val thumbnailUrl: String
)

fun AlbumEntity.toAlbum(): Album =
    Album(this.albumId, this.id, this.title, this.url, this.thumbnailUrl)

fun Album.toAlbumEntity(): AlbumEntity =
    AlbumEntity(this.albumId, this.id, this.title, this.url, this.thumbnailUrl)



