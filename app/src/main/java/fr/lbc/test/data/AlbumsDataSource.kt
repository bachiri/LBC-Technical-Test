package fr.lbc.test.data


import io.reactivex.Completable
import io.reactivex.Single

interface AlbumsDataSource {
    fun saveAlbum(album: Album)
    fun saveAlbums(albums: List<Album>): Completable
    fun getAllAlbums(): Single<List<Album>>
    fun getAlbums(from: Int, to: Int): Single<List<Album>>
    fun getAlbumById(id: Int): Single<Album>
    fun getAlbumsCount(): Single<Int>
    fun clearAlbums()

}