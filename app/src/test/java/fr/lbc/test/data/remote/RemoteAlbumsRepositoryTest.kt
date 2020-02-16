package fr.lbc.test.data.remote

import fr.lbc.test.data.Album
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test


import org.mockito.Mockito.*


class RemoteAlbumsRepositoryTest {

    private val album = Album(1, 2, "Title One", "UrlOne", "ThumbnailOne")
    private val oneAlbum: List<Album> = listOf(album)
    lateinit var remoteAlbumsRepository: RemoteAlbumsRepository

    @Test
    fun `When Calling API Should Return One Value`() {

        val albumApi: AlbumApi = mock(AlbumApi::class.java)
        remoteAlbumsRepository = RemoteAlbumsRepository(albumApi)

        `when`(albumApi.loadAlbums()).thenReturn(Single.just(oneAlbum))
        val allAlbums: Single<List<Album>> = remoteAlbumsRepository.getAllAlbums()

        verify(albumApi).loadAlbums()
        assertEquals(allAlbums.blockingGet().size, 1)
    }

    @Test(expected = Exception::class)
    fun `When Trying To Save Album To The Api Expect Exception`() {
        val albumApi: AlbumApi = mock(AlbumApi::class.java)
        remoteAlbumsRepository = RemoteAlbumsRepository(albumApi)
        remoteAlbumsRepository.saveAlbum(album)

    }

    @Test(expected = Exception::class)
    fun `When trying To save Albums To The Api Expect Exception`() {
        val albumApi: AlbumApi = mock(AlbumApi::class.java)
        remoteAlbumsRepository = RemoteAlbumsRepository(albumApi)
        remoteAlbumsRepository.saveAlbums(oneAlbum)
    }

    @Test(expected = Exception::class)
    fun `When trying To Clear Albums From The Api Expect Exception`() {
        val albumApi: AlbumApi = mock(AlbumApi::class.java)
        remoteAlbumsRepository = RemoteAlbumsRepository(albumApi)
        remoteAlbumsRepository.clearAlbums()
    }

    @Test(expected = Exception::class)
    fun `When trying To Get An Album By Id From API Then Expect Exception`() {
        val albumApi: AlbumApi = mock(AlbumApi::class.java)
        remoteAlbumsRepository = RemoteAlbumsRepository(albumApi)
        remoteAlbumsRepository.getAlbumById(1)
    }

    @Test(expected = Exception::class)
    fun `When trying To Get Albums Count From The API Then Expect Exception`() {
        val albumApi: AlbumApi = mock(AlbumApi::class.java)
        remoteAlbumsRepository = RemoteAlbumsRepository(albumApi)
        remoteAlbumsRepository.getAlbumsCount()
    }

    @Test(expected = Exception::class)
    fun `When trying To Get Albums Range From The API Then Expect Exception`() {
        val albumApi: AlbumApi = mock(AlbumApi::class.java)
        remoteAlbumsRepository = RemoteAlbumsRepository(albumApi)
        remoteAlbumsRepository.getAlbums(0, 10)
    }


}