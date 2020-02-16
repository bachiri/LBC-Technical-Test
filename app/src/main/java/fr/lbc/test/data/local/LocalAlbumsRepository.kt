package fr.lbc.test.data.local

import fr.lbc.test.data.Album
import fr.lbc.test.data.AlbumsDataSource
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject


class LocalAlbumsRepository @Inject constructor(@Local private val albumDao: AlbumDao) :
    AlbumsDataSource {
    override fun clearAlbums() {
        albumDao.clearAlbums()
    }

    override fun getAlbumById(id: Int): Single<Album> {
        return albumDao.getAlbum(id).map { it.toAlbum() }
    }

    override fun saveAlbum(album: Album) {
        albumDao.insert(album.toAlbumEntity())
    }

    override fun saveAlbums(albums: List<Album>): Completable {
        return albumDao.insertAll(albums.map { it.toAlbumEntity() })
    }

    //Can Be slow needs oprimization or  preferably Use getAlbums(from,to)
    override fun getAllAlbums(): Single<List<Album>> {
        return albumDao.getAllAlbums()
            .map { items ->
                items.map {
                    it.toAlbum()
                }
            }
    }

    override fun getAlbumsCount(): Single<Int> = albumDao.getAlbumCount()

    override fun getAlbums(from: Int, to: Int): Single<List<Album>> {
        return albumDao
            .getAlbums(from, to)
            .map { items ->
                items.map {
                    it.toAlbum()
                }
            }

    }
}