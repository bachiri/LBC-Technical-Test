package fr.lbc.test.albums.domain

import fr.lbc.test.data.Album
import fr.lbc.test.data.AlbumsDataSource
import fr.lbc.test.data.local.Local
import io.reactivex.Single
import javax.inject.Inject

class AlbumPaginatorUseCaseImpl @Inject constructor(@Local private val localAlbumsRepository: AlbumsDataSource) :AlbumPaginatorUseCase{

    override fun getNextAlbums(from: Int, to: Int): Single<List<Album>> =
        localAlbumsRepository.getAlbums(from, to)

}