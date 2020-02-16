package fr.lbc.test.albums.domain

import com.nhaarman.mockitokotlin2.*
import fr.lbc.test.data.AlbumsDataSource
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import utils.TestAlbumFactory


class AlbumSaverUseCaseTest {

    private lateinit var remoteAlbumsRepository: AlbumsDataSource

    private lateinit var localAlbumsRepository: AlbumsDataSource

    private lateinit var albumSaverUseCaseImpl: AlbumSaverUseCaseImpl

    @Before
    fun init() {
        remoteAlbumsRepository = mock()
        localAlbumsRepository = mock()
        albumSaverUseCaseImpl = AlbumSaverUseCaseImpl(remoteAlbumsRepository, localAlbumsRepository)
    }

    @Test
    fun `When DownloadAndSaveAlbums Called Albums Should be Saved`() {
        whenever(remoteAlbumsRepository.getAllAlbums()).thenReturn(TestAlbumFactory.getAlbumsListSingle())
        val testObserver = albumSaverUseCaseImpl.downloadAndSaveAlbums().test()
        verify(localAlbumsRepository, times(3)).saveAlbum(any())
        testObserver.dispose()
    }

    @Test
    fun `When getAlbumsCount Called Call Local Album Repository`() {
        whenever(localAlbumsRepository.getAlbumsCount()).thenReturn(Single.just(3))
        albumSaverUseCaseImpl.getAlbumsCount()
        val testObserver = albumSaverUseCaseImpl.getAlbumsCount().test()
        val count: Int = localAlbumsRepository.getAlbumsCount().blockingGet()
        assertEquals(3, count)
        testObserver.dispose()
    }


}