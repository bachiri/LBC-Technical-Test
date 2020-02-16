package fr.lbc.test.albums.domain


import io.reactivex.Completable
import io.reactivex.Single

interface AlbumSaverUseCase {

    fun downloadAndSaveAlbums(): Completable

    fun getAlbumsCount(): Single<Int>
}