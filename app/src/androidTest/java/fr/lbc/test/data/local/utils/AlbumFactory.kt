package utils

import fr.lbc.test.data.Album
import fr.lbc.test.data.local.AlbumEntity
import fr.lbc.test.data.local.toAlbumEntity
import io.reactivex.Single

class AlbumFactory {

    companion object {

        private val albumOne = Album(
            1,
            1,
            "accusamus beatae ad facilis cum similique qui sunt",
            "https://via.placeholder.com/600/92c952",
            "https://via.placeholder.com/150/92c952"
        )
        private val albumTwo = Album(
            1,
            2,
            "reprehenderit est deserunt velit ipsam",
            "https://via.placeholder.com/600/771796",
            "https://via.placeholder.com/150/771796"
        )
        private val albumThree = Album(
            1,
            3,
            "officia porro iure quia iusto qui ipsa ut modi",
            "https://via.placeholder.com/600/24f355",
            "https://via.placeholder.com/150/24f355"
        )
        private val albumList: List<Album> = listOf(albumOne, albumTwo, albumThree)

        fun getAlbum(id: Int): Album {
            return when (id) {
                1 -> albumOne
                2 -> albumTwo
                else -> albumThree

            }
        }

        fun getAlbumsList(): List<Album> = albumList

        fun getAlbumsEntityList(): List<AlbumEntity> = albumList.map { it.toAlbumEntity() }

        fun getAlbumsListSingle(): Single<List<Album>> = Single.just(albumList)
    }
}