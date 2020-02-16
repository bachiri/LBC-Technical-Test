package fr.lbc.test.album.domain

import fr.lbc.test.data.Album
import fr.lbc.test.data.AlbumsDataSource
import fr.lbc.test.data.local.Local
import io.reactivex.Single
import javax.inject.Inject

class AlbumUseCase @Inject constructor(@Local val localAlbumsRepository: AlbumsDataSource) {

    fun getAlbumById(id: Int): Single<Album> = localAlbumsRepository.getAlbumById(id)
}
