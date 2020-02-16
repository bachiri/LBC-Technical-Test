package fr.lbc.test.albums.domain


import fr.lbc.test.data.AlbumsDataSource
import fr.lbc.test.data.local.Local
import fr.lbc.test.data.remote.Remote
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AlbumSaverUseCaseImpl @Inject constructor(
    @Remote private val remoteAlbumsRepository: AlbumsDataSource,
    @Local private val localAlbumsRepository: AlbumsDataSource
) : AlbumSaverUseCase {

    override fun downloadAndSaveAlbums(): Completable {
        return remoteAlbumsRepository
            .getAllAlbums()
            .subscribeOn(Schedulers.io())
            .map { albumList ->
                albumList.map { album ->
                    localAlbumsRepository.saveAlbum(album)
                }
            }.ignoreElement()
    }

    override fun getAlbumsCount(): Single<Int> = localAlbumsRepository.getAlbumsCount()
}