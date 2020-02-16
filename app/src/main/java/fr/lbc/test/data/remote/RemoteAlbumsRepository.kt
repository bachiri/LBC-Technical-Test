package fr.lbc.test.data.remote

import fr.lbc.test.data.Album
import fr.lbc.test.data.AlbumsDataSource
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RemoteAlbumsRepository @Inject constructor(@Remote val albumApi: AlbumApi) :
    AlbumsDataSource {
    override fun saveAlbum(album: Album) {
        throw Exception("Save Album Not Supported yet")
    }

    override fun clearAlbums() {
        throw Exception("Albums Deletion is Not Supported")
    }

    override fun saveAlbums(albums: List<Album>): Completable {
        throw Exception("Save Albums Not Supported yet")
    }

    override fun getAllAlbums(): Single<List<Album>> {
        return albumApi
            .loadAlbums()
            .subscribeOn(Schedulers.io())
    }

    override fun getAlbums(from: Int, to: Int): Single<List<Album>> {
        throw Exception("Pagination Not supported yet ")
    }

    override fun getAlbumsCount(): Single<Int> {
        throw Exception("API Doesn't have count Feature Use {@link #getAlbums(int, int) getAlbums} method. and count them manually")
    }

    override fun getAlbumById(id: Int): Single<Album> {
        throw Exception("API Doesn't Support Yet Retrieving Only One Album -> Save Albums to Database -> Use Local storag")
    }

// TOD O Think About Refactoring to Flowable
}