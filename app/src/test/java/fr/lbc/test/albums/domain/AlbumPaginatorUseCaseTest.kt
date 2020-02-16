package fr.lbc.test.albums.domain

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import fr.lbc.test.data.Album
import fr.lbc.test.data.AlbumsDataSource
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import utils.TestAlbumFactory

class AlbumPaginatorUseCaseTest {

    private lateinit var localAlbumsRepository: AlbumsDataSource
    private lateinit var albumPaginatorUseCaseImpl: AlbumPaginatorUseCaseImpl
    @Before
    fun init() {
        localAlbumsRepository = mock()
        albumPaginatorUseCaseImpl = AlbumPaginatorUseCaseImpl(localAlbumsRepository)
    }

    @Test
    fun `When getNextAlbum is Called Albums Within Range Should be returned`() {
        whenever(
            albumPaginatorUseCaseImpl.getNextAlbums(
                any(),
                any()
            )
        ).thenReturn(TestAlbumFactory.getAlbumsListSingle())
        val test: TestObserver<List<Album>> =
            albumPaginatorUseCaseImpl.getNextAlbums(any(), any()).test()
        test.assertValues(TestAlbumFactory.getAlbumsList())
    }

}